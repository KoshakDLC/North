#version 330 core
in vec2 vUv;
in vec2 vLocal;
in vec2 vScreen;

uniform vec2 uViewport;
uniform vec3 uAccentTop;
uniform vec3 uAccentBottom;
uniform float uTime;
uniform float uEntry;
uniform float uPulse;
uniform float uLightMode;
uniform vec2 uMouse;
uniform vec2 uLocalMouse;
uniform vec2 uMouseVelocity;

out vec4 FragColor;

vec3 srgb_to_linear(vec3 c) {
    return vec3(
        c.r <= 0.04045 ? c.r / 12.92 : pow((c.r + 0.055) / 1.055, 2.4),
        c.g <= 0.04045 ? c.g / 12.92 : pow((c.g + 0.055) / 1.055, 2.4),
        c.b <= 0.04045 ? c.b / 12.92 : pow((c.b + 0.055) / 1.055, 2.4)
    );
}

vec3 linear_to_srgb(vec3 c) {
    c = clamp(c, 0.0, 1.0);
    return vec3(
        c.r <= 0.0031308 ? c.r * 12.92 : 1.055 * pow(c.r, 0.4166666666666667) - 0.055,
        c.g <= 0.0031308 ? c.g * 12.92 : 1.055 * pow(c.g, 0.4166666666666667) - 0.055,
        c.b <= 0.0031308 ? c.b * 12.92 : 1.055 * pow(c.b, 0.4166666666666667) - 0.055
    );
}

vec3 linear_srgb_to_oklab(vec3 c) {
    float l = 0.4122214708 * c.r + 0.5363325363 * c.g + 0.0514459929 * c.b;
    float m = 0.2119034982 * c.r + 0.6806995451 * c.g + 0.1073969566 * c.b;
    float s = 0.0883024619 * c.r + 0.2817188376 * c.g + 0.6299787005 * c.b;
    float l_ = sign(l) * pow(abs(l), 0.333333333333);
    float m_ = sign(m) * pow(abs(m), 0.333333333333);
    float s_ = sign(s) * pow(abs(s), 0.333333333333);
    return vec3(
        0.2104542553 * l_ + 0.7936177850 * m_ - 0.0040720468 * s_,
        1.9779984951 * l_ - 2.4285922050 * m_ + 0.4505937099 * s_,
        0.0259040371 * l_ + 0.7827717662 * m_ - 0.8086757660 * s_
    );
}

vec3 oklab_to_linear_srgb(vec3 c) {
    float l_ = c.x + 0.3963377774 * c.y + 0.2158037573 * c.z;
    float m_ = c.x - 0.1055613458 * c.y - 0.0638541728 * c.z;
    float s_ = c.x - 0.0894841775 * c.y - 1.2914855480 * c.z;
    float l = l_ * l_ * l_;
    float m = m_ * m_ * m_;
    float s = s_ * s_ * s_;
    return vec3(
        4.0767416621 * l - 3.3077115913 * m + 0.2309699292 * s,
        -1.2684380046 * l + 2.6097574011 * m - 0.3413193965 * s,
        -0.0041960863 * l - 0.7034186147 * m + 1.7076147010 * s
    );
}

vec3 oklab_mix(vec3 colA, vec3 colB, float t) {
    vec3 labA = linear_srgb_to_oklab(colA);
    vec3 labB = linear_srgb_to_oklab(colB);
    return oklab_to_linear_srgb(mix(labA, labB, t));
}

vec3 oklab_mix_srgb(vec3 colA, vec3 colB, float t) {
    return linear_to_srgb(oklab_mix(srgb_to_linear(colA), srgb_to_linear(colB), clamp(t, 0.0, 1.0)));
}

float hash12(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + vec3(33.33));
    return fract((p3.x + p3.y) * p3.z);
}

float arm(float x, float power) {
    return pow(max(x, 0.0), power);
}

mat2 rot2(float a) {
    float s = sin(a);
    float c = cos(a);
    return mat2(c, -s, s, c);
}

float sdEllipseLight(vec2 p, vec2 r) {
    return (length(p / max(r, vec2(0.0001))) - 1.0) * min(r.x, r.y);
}

void main() {
    vec2 p = vUv - 0.5;
    float r = length(p);
    float a = atan(p.y, p.x);
    vec2 lm = uLocalMouse - 0.5;
    float lmDist = length(lm);
    float lmField = (1.0 - smoothstep(0.16, 0.82, lmDist)) * exp(-lmDist * lmDist * 2.4);
    vec2 lmDir = lm / max(lmDist, 0.08);
    float speed = clamp(length(uMouseVelocity) * 0.85, 0.0, 1.0);
    float angularPull = dot(vec2(cos(a), sin(a)), lmDir);
    float phasePull = lmField * (angularPull * 0.34 + speed * 0.10 * sin(a * 2.0 - uTime * 0.55));
    float entry = smoothstep(0.0, 1.0, uEntry);
    float reveal = 1.0 - entry;
    float pulse = 0.90 + 0.10 * uPulse;
    float drift = uTime * (0.48 + speed * 0.045) + lmField * 0.055;
    vec2 edgeA = smoothstep(vec2(0.00), vec2(0.18), vUv);
    vec2 edgeB = vec2(1.0) - smoothstep(vec2(0.82), vec2(1.00), vUv);
    float edgeMask = edgeA.x * edgeA.y * edgeB.x * edgeB.y;
    if (uLightMode > 0.5) {
        float blossom = 0.0;
        float vein = 0.0;
        for (int i = 0; i < 8; i++) {
            float fi = float(i);
            float angle = fi * 0.78539816339 + sin(uTime * 0.18 + fi) * 0.018;
            vec2 q = rot2(angle) * p;
            q.y -= 0.145 + 0.014 * sin(uTime * 0.26 + fi * 1.7);
            q.x *= 0.88;
            float d = sdEllipseLight(q, vec2(0.050, 0.188));
            float petal = 1.0 - smoothstep(-0.010, 0.018, d);
            float fade = 1.0 - smoothstep(0.10, 0.29, length(q));
            blossom = max(blossom, petal * fade);
            vein += exp(-abs(q.x) * 74.0) * smoothstep(-0.055, 0.060, q.y) * (1.0 - smoothstep(0.055, 0.185, q.y)) * petal * fade;
        }
        float halo = exp(-r * r * 13.5);
        float wash = exp(-r * r * 4.2);
        float breatheLight = 0.86 + 0.14 * sin(uTime * 0.72);
        vec3 accent = oklab_mix_srgb(uAccentBottom, uAccentTop, 0.58 + 0.18 * sin(uTime * 0.18));
        vec3 petalColor = oklab_mix_srgb(vec3(1.0, 0.88, 0.92), accent, 0.34);
        vec3 color = petalColor * blossom * (0.26 + 0.04 * breatheLight);
        color += accent * halo * 0.070;
        color += vec3(1.0, 0.84, 0.76) * wash * 0.040;
        color -= vec3(0.055, 0.010, 0.022) * vein * 0.028;
        float alpha = (blossom * 0.175 + halo * 0.080 + wash * 0.035) * entry * edgeMask;
        if (alpha <= 0.002) {
            discard;
        }
        FragColor = vec4(clamp(color, 0.0, 1.0), clamp(alpha, 0.0, 0.26));
        return;
    }
    float organicEdge = 1.0 - smoothstep(0.22, 0.78, r + 0.045 * sin(a * 7.0 + uTime * 0.23) + 0.025 * sin(a * 13.0 - uTime * 0.17));
    float apertureWarp = 0.78 + 0.22 * sin(a * 5.0 - uTime * 0.34 + phasePull + sin(r * 12.0 + uTime * 0.22));
    float aperture = exp(-r * r * (2.35 + apertureWarp * 0.82)) * apertureWarp * organicEdge * edgeMask;
    float centerFade = smoothstep(0.012, 0.12, r);
    float swirlA = arm(0.5 + 0.5 * cos(a * 6.0 - r * 20.0 + drift + phasePull), 3.2);
    float swirlB = arm(0.5 + 0.5 * cos(a * 6.0 + r * 16.0 - drift * 0.88 + 1.74 - phasePull * 0.72), 4.0);
    float swirlC = arm(0.5 + 0.5 * cos(a * 12.0 - r * 27.0 + drift * 0.54 + phasePull * 0.58), 6.2);
    float swirlD = arm(0.5 + 0.5 * cos(a * 18.0 + r * 20.0 - drift * 1.15 - phasePull * 0.24), 9.0);
    float curl = (swirlA * 0.92 + swirlB * 0.62 + swirlC * 0.32 + swirlD * 0.14) * aperture * centerFade;
    float shear = arm(0.5 + 0.5 * cos(a * 3.0 + r * 18.0 + uTime * 0.31), 2.7) * exp(-r * r * 7.5) * organicEdge * edgeMask;
    float core = exp(-r * r * 20.0) * edgeMask;
    float bloom = exp(-r * r * 4.2) * aperture;
    float revealFlare = exp(-r * r * (5.8 - reveal * 2.5)) * reveal * aperture;
    float mouseD = length(vScreen - uMouse);
    float mouseLift = exp(-mouseD * mouseD / 42000.0) * aperture;
    float cursorWake = exp(-dot(p - lm, p - lm) * 4.8) * lmField * organicEdge * edgeMask;
    float breathe = 0.88 + 0.12 * sin(uTime * 1.24);
    vec3 accent = oklab_mix_srgb(uAccentBottom, uAccentTop, clamp(0.52 + p.y * 1.2 + sin(a - uTime * 0.28) * 0.14, 0.0, 1.0));
    vec3 hot = oklab_mix_srgb(vec3(1.0), accent, 0.32);
    vec3 color = vec3(0.0);
    color += accent * bloom * 0.24 * pulse;
    color += accent * curl * 0.76 * breathe;
    color += hot * shear * 0.18;
    color += hot * revealFlare * 0.34;
    color += accent * mouseLift * 0.26;
    color += oklab_mix_srgb(hot, accent, 0.35) * cursorWake * (0.030 + speed * 0.075);
    color += hot * core * 0.14;
    color += (hash12(vScreen + uTime * 23.0) - 0.5) * vec3(0.0012);
    float alpha = clamp(bloom * 0.20 + curl * 0.72 + shear * 0.17 + revealFlare * 0.24 + mouseLift * 0.12 + cursorWake * 0.035 + core * 0.10, 0.0, 0.82) * entry * edgeMask * organicEdge;
    if (alpha <= 0.002) {
        discard;
    }
    FragColor = vec4(color, alpha);
}
