#version 330 core

in vec2 vUv;
in vec2 vLocal;
in vec2 vScreen;

uniform vec2 uResolution;
uniform vec2 uMouse;
uniform vec2 uParallax;
uniform vec3 uAccentTop;
uniform vec3 uAccentBottom;
uniform float uTime;
uniform float uEntry;
uniform float uLightMode;
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

float hash12(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + vec3(33.33));
    return fract((p3.x + p3.y) * p3.z);
}

vec2 hash22(vec2 p) {
    return vec2(hash12(p), hash12(p + vec2(13.71)));
}

float sdSegment(vec2 p, vec2 a, vec2 b) {
    vec2 pa = p - a;
    vec2 ba = b - a;
    float h = clamp(dot(pa, ba) / max(dot(ba, ba), 0.0001), 0.0, 1.0);
    return length(pa - ba * h);
}

void main() {
    vec2 px = vScreen + uParallax * 0.35;
    vec2 mouse = uMouse * uResolution;
    vec3 color = vec3(0.0);
    float alpha = 0.0;
    for (int i = 0; i < 14; i++) {
        float fi = float(i);
        vec2 seed = vec2(fi * 11.37, fi * 4.19);
        vec2 h = hash22(seed);
        float depth = mix(0.28, 0.92, hash12(seed + 3.4));
        vec2 center = vec2(h.x * uResolution.x, h.y * uResolution.y);
        center += vec2(sin(uTime * 0.055 + fi) * 44.0, cos(uTime * 0.041 + fi * 1.8) * 28.0) * depth;
        center += uParallax * depth * 0.55;
        center = mod(center + uResolution, uResolution);
        float angle = hash12(seed + 7.2) * 6.283185 + sin(uTime * 0.07 + fi) * 0.25;
        vec2 dir = vec2(cos(angle), sin(angle));
        float len = mix(44.0, 118.0, depth);
        float d = sdSegment(px, center - dir * len, center + dir * len);
        float along = abs(dot(px - center, dir)) / len;
        float veil = exp(-d * d / mix(90.0, 260.0, depth)) * (1.0 - smoothstep(0.10, 1.0, along));
        float flicker = 0.70 + 0.30 * sin(uTime * (0.28 + depth * 0.15) + fi * 2.1);
        vec3 tint = oklab_mix_srgb(uAccentBottom, uAccentTop, hash12(seed + 2.7));
        color += tint * veil * flicker * 0.020;
        alpha += veil * flicker * 0.010;
    }
    float md = length(vScreen - mouse);
    float mouseVeil = exp(-md * md / 22000.0);
    color += oklab_mix_srgb(uAccentBottom, vec3(1.0), 0.16) * mouseVeil * 0.026;
    alpha += mouseVeil * 0.014;
    for (int i = 0; i < 14; i++) {
        vec4 tr = uTrail[i];
        float live = tr.w * (1.0 - smoothstep(0.0, 3.2, tr.z));
        vec2 pos = tr.xy * uResolution;
        float d = length(vScreen - pos);
        float veil = exp(-d * d / 16500.0) * live;
        color += oklab_mix_srgb(uAccentTop, uAccentBottom, 0.45) * veil * 0.034;
        alpha += veil * 0.018;
    }
    alpha *= smoothstep(0.0, 0.9, uEntry);
    if (uLightMode > 0.5) {
        color *= 0.55;
        alpha *= 0.42;
    }
    FragColor = vec4(color, clamp(alpha, 0.0, 0.085));
}
