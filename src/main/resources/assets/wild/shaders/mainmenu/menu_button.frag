#version 330 core

in vec2 vUv;
in vec2 vLocal;
in vec2 vScreen;

uniform sampler2D uBackground;
uniform vec2 uViewport;
uniform vec2 uTextureSize;
uniform vec4 uButton;
uniform vec2 uMouse;
uniform vec2 uLocalMouse;
uniform vec3 uAccentTop;
uniform vec3 uAccentBottom;
uniform float uRadius;
uniform float uTime;
uniform float uHover;
uniform float uMagnet;
uniform float uPress;
uniform float uEntry;
uniform float uFlash;
uniform float uScale;
uniform float uVelocity;
uniform float uButtonVelocity;
uniform float uLightMode;

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

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);
    float a = hash12(i);
    float b = hash12(i + vec2(1.0, 0.0));
    float c = hash12(i + vec2(0.0, 1.0));
    float d = hash12(i + vec2(1.0, 1.0));
    return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

float sdRoundBox(vec2 p, vec2 b, float r) {
    r = min(r, min(b.x, b.y));
    vec2 q = abs(p) - b + r;
    return min(max(q.x, q.y), 0.0) + length(max(q, vec2(0.0))) - r;
}

float coverage(float d) {
    float px = max(fwidth(d) * 0.95, 0.0001);
    return 1.0 - smoothstep(-px, px, d);
}

vec2 screenUv(vec2 screen) {
    return vec2(screen.x / max(uViewport.x, 1.0), 1.0 - screen.y / max(uViewport.y, 1.0));
}

vec3 back(vec2 uv) {
    return texture(uBackground, clamp(uv, vec2(0.0), vec2(1.0))).rgb;
}

float cycle01(float phase) {
    return 0.5 - 0.5 * cos(fract(phase) * 6.28318530718);
}

void main() {
    vec2 local = vLocal - uButton.xy;
    vec2 size = max(uButton.zw, vec2(1.0));
    vec2 rectSize = max(size + uButton.xy * 2.0, vec2(1.0));
    vec2 quadUv = vLocal / rectSize;
    vec2 edgeA = smoothstep(vec2(0.00), vec2(0.16), quadUv);
    vec2 edgeB = vec2(1.0) - smoothstep(vec2(0.84), vec2(1.00), quadUv);
    float quadFade = edgeA.x * edgeA.y * edgeB.x * edgeB.y;
    vec2 halfSize = size * 0.5;
    vec2 p = (local - halfSize) / max(uScale, 0.001);
    float baseD = sdRoundBox(p, halfSize - vec2(2.0), uRadius);
    float edgeBand = 1.0 - smoothstep(0.0, 28.0, abs(baseD));
    float angle = atan(p.y, p.x / max(size.x / max(size.y, 1.0), 1.0));
    vec2 mouseLocal = uLocalMouse * size - halfSize;
    float mouseD = length((local - uLocalMouse * size) / vec2(max(size.y, 1.0)));
    float attract = exp(-mouseD * mouseD * 2.8) * uMagnet;
    float ferroWave = 0.50 + 0.50 * sin(angle * 18.0 + uTime * 3.8 + sin(angle * 5.0 - uTime * 1.1));
    float ferroFine = 0.50 + 0.50 * sin(angle * 31.0 - uTime * 5.2);
    float mouseSide = max(dot(normalize(p + vec2(0.001)), normalize(mouseLocal + vec2(0.001))), 0.0);
    float ferro = edgeBand * uMagnet * (1.6 + attract * 7.0) * (0.34 + ferroWave * 0.46 + ferroFine * 0.20) * (0.38 + mouseSide * 0.92);
    float d = baseD - ferro;
    float innerD = sdRoundBox(p, halfSize - vec2(10.0, 7.0), max(uRadius - 6.0, 1.0));
    float shape = coverage(d);
    float inner = coverage(innerD);
    float entry = smoothstep(0.0, 1.0, uEntry);
    vec2 uv = screenUv(vScreen);
    vec2 normal = normalize(vec2(dFdx(d), dFdy(d)) + vec2(0.0001));
    float rim = (1.0 - smoothstep(0.0, 1.55, abs(d))) * shape;
    float bevel = (1.0 - smoothstep(0.0, 10.0, abs(baseD))) * shape;
    float topLightG = (local.y - size.y * 0.12) / max(size.y * 0.15, 1.0);
    float topLight = exp(-topLightG * topLightG) * shape;
    float bottomShadeG = (local.y - size.y * 0.90) / max(size.y * 0.22, 1.0);
    float bottomShade = exp(-bottomShadeG * bottomShadeG) * shape;
    float railTop = exp(-(local.y - 2.0) * (local.y - 2.0) / 7.5) * smoothstep(0.06, 0.22, local.x / size.x) * (1.0 - smoothstep(0.78, 0.94, local.x / size.x));
    float railBottom = exp(-(local.y - size.y + 2.0) * (local.y - size.y + 2.0) / 9.5) * smoothstep(0.06, 0.24, local.x / size.x) * (1.0 - smoothstep(0.76, 0.94, local.x / size.x));
    float ferroVeins = pow(0.5 + 0.5 * sin(local.x * 0.042 + sin(local.y * 0.11 + uTime) * 1.8 - uTime * 0.42), 4.0) * inner * uMagnet;
    vec2 glowDelta = (local / size - uLocalMouse) * vec2(size.x / max(size.y, 1.0), 1.0);
    float internalGlow = exp(-dot(glowDelta, glowDelta) * 4.6) * uMagnet * shape;
    float sweepG = local.x / size.x - cycle01(uTime * 0.045 + 0.18);
    float sweep = exp(-sweepG * sweepG * 76.0) * shape * (0.12 + uMagnet * 0.42);
    float entryG = local.x / size.x - uEntry;
    float entrySweep = exp(-entryG * entryG * 42.0) * shape * (1.0 - smoothstep(0.76, 1.0, uEntry));
    float refraction = (1.65 + uMagnet * 2.25 + uHover * 0.90) * inner;
    vec3 refracted = back(uv - normal * refraction / max(uTextureSize, vec2(1.0))) * 0.78 + back(uv) * 0.22;
    float chroma = rim * uMagnet * clamp(uButtonVelocity * 1.35 + uVelocity * 0.08, 0.0, 1.0);
    vec2 split = normal * chroma * 3.4 / max(uTextureSize, vec2(1.0));
    vec3 ca = vec3(back(uv + split).r, back(uv).g, back(uv - split).b);
    float shadowD = sdRoundBox(p - vec2(0.0, 13.0 + uMagnet * 4.0), halfSize - vec2(3.0), uRadius);
    float shadowEdge = (1.0 - smoothstep(0.0, 54.0, max(shadowD, 0.0))) * smoothstep(-18.0, 18.0, shadowD);
    float shadow = shadowEdge * (0.034 + uMagnet * 0.082) * entry;
    float grain = (noise(local * 0.018 + vec2(uTime * 0.010, -uTime * 0.012)) - 0.5) * 0.012;
    vec3 accent = oklab_mix_srgb(uAccentBottom, uAccentTop, clamp(local.y / size.y + ferroVeins * 0.10, 0.0, 1.0));
    vec3 glass = oklab_mix_srgb(vec3(0.018, 0.020, 0.026), vec3(0.038, 0.048, 0.061), uMagnet);
    glass += refracted * 0.098;
    glass = mix(glass, ca * 0.16 + glass * 0.84, chroma);
    glass += vec3(grain);
    vec3 color = accent * shadow * 0.30;
    color += glass * shape;
    color += vec3(1.0) * topLight * 0.035;
    color -= vec3(0.016, 0.013, 0.018) * bottomShade;
    color += accent * ferroVeins * 0.060;
    color += accent * internalGlow * 0.34;
    color += oklab_mix_srgb(vec3(1.0), accent, 0.45) * (railTop * 0.110 + railBottom * 0.068);
    color += oklab_mix_srgb(vec3(1.0), accent, 0.40) * bevel * (0.018 + uMagnet * 0.040);
    color += oklab_mix_srgb(vec3(1.0), accent, 0.28) * (sweep * 0.060 + entrySweep * 0.105);
    color += oklab_mix_srgb(vec3(1.0), accent, 0.36) * rim * (0.082 + uMagnet * 0.160 + uFlash * 0.30);
    color += vec3(1.0) * uFlash * shape * 0.13;
    float alpha = max(shadow * 0.46, shape * (0.78 + uMagnet * 0.08) + rim * 0.11 + internalGlow * 0.045 + railTop * 0.035) * entry * quadFade;
    if (uLightMode > 0.5) {
        vec3 lightBase = oklab_mix_srgb(vec3(1.0), accent, 0.045 + uMagnet * 0.035);
        vec3 lightRefract = oklab_mix_srgb(refracted, vec3(1.0), 0.82);
        vec3 lightGlass = mix(lightBase, lightRefract, 0.16 + inner * 0.08);
        vec3 lightEdge = oklab_mix_srgb(vec3(1.0), accent, 0.22);
        color = vec3(0.0) * shadow * (1.0 - shape);
        color += lightGlass * shape;
        color += vec3(1.0) * topLight * 0.105;
        color -= vec3(0.045, 0.038, 0.040) * bottomShade * 0.18;
        color += accent * internalGlow * 0.060;
        color += lightEdge * (railTop * 0.080 + railBottom * 0.040);
        color += lightEdge * bevel * (0.030 + uMagnet * 0.035);
        color += lightEdge * rim * (0.060 + uMagnet * 0.055 + uFlash * 0.10);
        color += vec3(1.0, 0.84, 0.80) * uFlash * shape * 0.075;
        alpha = max(shadow * (1.0 - shape) * 0.34, shape * (0.70 + uMagnet * 0.06) + rim * 0.055 + internalGlow * 0.020 + railTop * 0.025) * entry * quadFade;
    }
    if (alpha <= 0.002) {
        discard;
    }
    FragColor = vec4(color, clamp(alpha, 0.0, 1.0));
}
