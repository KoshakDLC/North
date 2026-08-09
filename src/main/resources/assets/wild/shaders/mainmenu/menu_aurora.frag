#version 330 core

in vec2 vUv;
in vec2 vLocal;
in vec2 vScreen;

uniform vec2 uResolution;
uniform vec2 uMouse;
uniform vec2 uMouseVelocity;
uniform vec3 uAccentTop;
uniform vec3 uAccentBottom;
uniform float uTime;
uniform float uActivity;
uniform vec4 uTrail[14];

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

vec3 mod289(vec3 x) {
    return x - floor(x * (1.0 / 289.0)) * 289.0;
}

vec2 mod289(vec2 x) {
    return x - floor(x * (1.0 / 289.0)) * 289.0;
}

vec3 permute(vec3 x) {
    return mod289(((x * 34.0) + 1.0) * x);
}

float snoise(vec2 v) {
    const vec4 C = vec4(0.211324865405187, 0.366025403784439, -0.577350269189626, 0.024390243902439);
    vec2 i = floor(v + dot(v, C.yy));
    vec2 x0 = v - i + dot(i, C.xx);
    vec2 i1 = x0.x > x0.y ? vec2(1.0, 0.0) : vec2(0.0, 1.0);
    vec4 x12 = x0.xyxy + C.xxzz;
    x12.xy -= i1;
    i = mod289(i);
    vec3 p = permute(permute(i.y + vec3(0.0, i1.y, 1.0)) + i.x + vec3(0.0, i1.x, 1.0));
    vec3 m = max(0.5 - vec3(dot(x0, x0), dot(x12.xy, x12.xy), dot(x12.zw, x12.zw)), 0.0);
    m = m * m;
    m = m * m;
    vec3 x = 2.0 * fract(p * C.www) - 1.0;
    vec3 h = abs(x) - 0.5;
    vec3 ox = floor(x + 0.5);
    vec3 a0 = x - ox;
    m *= 1.79284291400159 - 0.85373472095314 * (a0 * a0 + h * h);
    vec3 g;
    g.x = a0.x * x0.x + h.x * x0.y;
    g.yz = a0.yz * x12.xz + h.yz * x12.yw;
    return 130.0 * dot(m, g);
}

float fbm(vec2 p) {
    float v = 0.0;
    float a = 0.5;
    mat2 r = mat2(0.82, -0.57, 0.57, 0.82);
    for (int i = 0; i < 5; i++) {
        v += snoise(p) * a;
        p = r * p * 1.58 + vec2(3.4, 2.6);
        a *= 0.49;
    }
    return v * 0.5 + 0.5;
}

float hash12(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + vec3(33.33));
    return fract((p3.x + p3.y) * p3.z);
}

float filament(float x, float power) {
    return pow(1.0 - abs(sin(x)), power);
}

void main() {
    vec2 uv = vLocal / max(uResolution, vec2(1.0));
    float aspect = uResolution.x / max(uResolution.y, 1.0);
    vec2 p = (uv - 0.5) * vec2(aspect, 1.0);
    vec2 mouse = (uMouse - 0.5) * vec2(aspect, 1.0);
    vec2 d = p - mouse;
    float mr = length(d);
    vec2 mn = d / max(mr, 0.001);
    vec2 tangent = vec2(-mn.y, mn.x);
    vec2 vel = vec2(uMouseVelocity.x * aspect, uMouseVelocity.y);
    float speed = clamp(length(vel) * 1.7, 0.0, 1.0);
    vec2 vdir = vel / max(length(vel), 0.001);
    vec2 vtan = vec2(-vdir.y, vdir.x);
    float front = dot(d, vdir);
    float side = dot(d, vtan);
    float wake = exp(-side * side * 24.0) * (1.0 - smoothstep(-0.04, 0.38, front)) * speed;
    float obstacle = exp(-mr * mr * 7.0) * clamp(uActivity + 0.16, 0.0, 1.0);
    vec2 flow = tangent * obstacle * (0.014 + speed * 0.018) - mn * obstacle * 0.004 - vdir * wake * 0.020 + vtan * sin(front * 21.0 - uTime * 1.7) * wake * 0.011;
    float trailAura = 0.0;
    for (int i = 0; i < 14; i++) {
        vec4 tr = uTrail[i];
        float live = tr.w * (1.0 - smoothstep(0.0, 3.2, tr.z));
        vec2 tp = (tr.xy - 0.5) * vec2(aspect, 1.0);
        vec2 td = p - tp;
        float a = exp(-dot(td, td) * 7.0) * live;
        trailAura += a;
        flow += vec2(-td.y, td.x) * a * 0.018;
    }
    float angle = atan(p.y, p.x);
    float radius = length(p);
    vec2 curl = vec2(cos(angle + uTime * 0.018), sin(angle + uTime * 0.018)) * (0.020 + radius * 0.010);
    vec2 domain = p * 0.68 + flow + curl;
    float t = uTime * 0.015;
    float velvet = fbm(domain * vec2(0.30, 0.22) + vec2(t * 0.12, -t * 0.09));
    float liquid = fbm(domain * 0.92 + vec2(t, -t * 0.74));
    float tension = fbm(domain * 1.36 + vec2(-t * 0.48, t * 0.38) + liquid * 0.22);
    float shear = fbm(domain * vec2(0.70, 0.42) + vec2(t * 0.33, t * 0.19));
    float oil = filament((domain.x * 1.15 - domain.y * 0.62 + tension * 1.75 + uTime * 0.055) * 3.141592, 9.0);
    float oilB = filament((domain.x * -0.50 + domain.y * 1.12 + liquid * 1.25 - uTime * 0.041) * 3.141592, 10.0);
    float spec = oil * 0.58 + oilB * 0.42;
    float sheet = smoothstep(0.38, 0.92, liquid * 0.46 + tension * 0.34 + shear * 0.20);
    float depth = 1.0 - smoothstep(0.08, 1.28, length((uv - 0.5) * vec2(aspect * 0.72, 1.0)));
    float edge = 1.0 - smoothstep(0.20, 1.18, length((uv - 0.5) * vec2(aspect, 1.0)));
    vec3 canvas = vec3(8.0, 9.0, 12.0) / 255.0;
    vec3 base = vec3(0.012, 0.014, 0.019) + vec3(0.010, 0.012, 0.016) * velvet;
    vec3 accent = oklab_mix_srgb(uAccentBottom, uAccentTop, clamp(uv.y * 0.58 + tension * 0.42, 0.0, 1.0));
    vec3 steel = oklab_mix_srgb(accent, vec3(0.70, 0.82, 0.95), 0.16);
    float energy = sheet * 0.038 + spec * 0.036 + trailAura * 0.030 + obstacle * 0.026 + wake * 0.034;
    vec3 color = canvas + base + steel * energy * (0.48 + depth * 0.52);
    color += steel * spec * depth * 0.024;
    color *= 0.82 + edge * 0.18;
    color += (hash12(vScreen + uTime * 13.0) - 0.5) * vec3(0.0024);
    FragColor = vec4(color, 1.0);
}
