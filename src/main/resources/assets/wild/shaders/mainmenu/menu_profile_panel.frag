#version 330 core

in vec2 vUv;
in vec2 vLocal;
in vec2 vScreen;

uniform sampler2D uBackground;
uniform vec2 uViewport;
uniform vec2 uTextureSize;
uniform vec4 uContent;
uniform vec2 uMouse;
uniform vec2 uLocalMouse;
uniform vec3 uAccentTop;
uniform vec3 uAccentBottom;
uniform float uRadius;
uniform float uTime;
uniform float uHover;
uniform float uPress;
uniform float uFlash;
uniform float uSelected;
uniform float uEntry;
uniform float uScale;
uniform float uVelocity;
uniform float uSurfaceVelocity;
uniform float uPanelProgress;
uniform float uLightMode;
uniform float uGlassBlur;
uniform float uGlassTint;
uniform float uInteriorGlow;
uniform float uFilmGrain;
uniform int uMode;

out vec4 FragColor;

float hash12(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + vec3(33.33));
    return fract((p3.x + p3.y) * p3.z);
}

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

vec3 oklab_mix_srgb(vec3 a, vec3 b, float t) {
    vec3 la = linear_srgb_to_oklab(srgb_to_linear(a));
    vec3 lb = linear_srgb_to_oklab(srgb_to_linear(b));
    return linear_to_srgb(oklab_to_linear_srgb(mix(la, lb, clamp(t, 0.0, 1.0))));
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

vec3 sampleBack(vec2 uv) {
    return texture(uBackground, clamp(uv, vec2(0.0), vec2(1.0))).rgb;
}

vec3 frosted(vec2 uv, float radius) {
    vec2 px = radius / max(uTextureSize, vec2(1.0));
    vec3 c = sampleBack(uv) * 0.240;
    c += sampleBack(uv + vec2(px.x, 0.0)) * 0.105;
    c += sampleBack(uv - vec2(px.x, 0.0)) * 0.105;
    c += sampleBack(uv + vec2(0.0, px.y)) * 0.105;
    c += sampleBack(uv - vec2(0.0, px.y)) * 0.105;
    c += sampleBack(uv + vec2(px.x, px.y) * 0.72) * 0.060;
    c += sampleBack(uv + vec2(-px.x, px.y) * 0.72) * 0.060;
    c += sampleBack(uv + vec2(px.x, -px.y) * 0.72) * 0.060;
    c += sampleBack(uv + vec2(-px.x, -px.y) * 0.72) * 0.060;
    c += sampleBack(uv + vec2(px.x * 1.85, px.y * 0.45)) * 0.035;
    c += sampleBack(uv + vec2(-px.x * 1.85, -px.y * 0.45)) * 0.035;
    c += sampleBack(uv + vec2(px.x * 0.45, -px.y * 1.85)) * 0.035;
    c += sampleBack(uv + vec2(-px.x * 0.45, px.y * 1.85)) * 0.035;
    return c;
}

void main() {
    vec2 local = vLocal - uContent.xy;
    vec2 size = max(uContent.zw, vec2(1.0));
    vec2 rectSize = max(size + uContent.xy * 2.0, vec2(1.0));
    vec2 quadUv = vLocal / rectSize;
    vec2 edgeA = smoothstep(vec2(0.00), vec2(0.18), quadUv);
    vec2 edgeB = vec2(1.0) - smoothstep(vec2(0.82), vec2(1.00), quadUv);
    float quadFade = edgeA.x * edgeA.y * edgeB.x * edgeB.y;
    vec2 halfSize = size * 0.5;
    vec2 p = (local - halfSize) / max(uScale, 0.001);
    float d = sdRoundBox(p, halfSize - vec2(1.0), uRadius);
    float shape = coverage(d);
    vec2 uv = screenUv(vScreen);
    vec2 grad = normalize(vec2(dFdx(d), dFdy(d)) + vec2(0.0001));
    float modePanel = uMode == 1 ? 1.0 : 0.0;
    float modeOption = uMode == 2 ? 1.0 : 0.0;
    float modeControl = uMode == 3 ? 1.0 : 0.0;
    float entry = smoothstep(0.0, 1.0, uEntry);
    float hover = clamp(uHover, 0.0, 1.0);
    float selected = clamp(uSelected, 0.0, 1.0);
    float velocity = clamp(uVelocity * 0.12 + uSurfaceVelocity, 0.0, 1.0);
    vec2 mouseDelta = (local / size - uLocalMouse) * vec2(size.x / max(size.y, 1.0), 1.0);
    float magnetic = exp(-dot(mouseDelta, mouseDelta) * mix(5.8, 3.2, modePanel)) * (hover + selected * 0.48);
    float refraction = (1.3 + hover * 2.1 + selected * 1.2 + modePanel * 0.9) * shape;
    float blurScale = max(uGlassBlur, 0.1);
    float glowGate = clamp(uInteriorGlow, 0.0, 1.0);
    vec3 blurred = frosted(uv - grad * refraction / max(uTextureSize, vec2(1.0)), (mix(9.0, 17.0, modePanel) + modeControl * 2.0 + hover * 3.0) * blurScale);
    vec3 accent = oklab_mix_srgb(uAccentBottom, uAccentTop, clamp(local.y / max(size.y, 1.0) + selected * 0.22, 0.0, 1.0));
    vec3 base = oklab_mix_srgb(vec3(0.014, 0.017, 0.023), vec3(0.044, 0.052, 0.067), modeOption * 0.42 + modeControl * 0.34 + hover * 0.22 + selected * 0.16);
    vec3 glass = mix(base, blurred, (0.105 + modePanel * 0.065 + modeControl * 0.035 + hover * 0.035) + uGlassTint * 0.105);
    float grain = (hash12(vScreen * 0.83 + vec2(uTime * 49.0, -uTime * 37.0)) - 0.5) * clamp(uFilmGrain, 0.0, 1.0);
    float rim = (1.0 - smoothstep(0.0, 1.45, abs(d))) * shape;
    float onePx = (1.0 - smoothstep(0.78, 1.85, abs(d))) * shape;
    float bevel = (1.0 - smoothstep(0.0, 10.5, abs(d))) * shape;
    float topRail = exp(-(local.y - 2.0) * (local.y - 2.0) / 8.5) * smoothstep(0.04, 0.16, local.x / size.x) * (1.0 - smoothstep(0.84, 0.96, local.x / size.x));
    float bottomShade = exp(-(local.y - size.y + 2.0) * (local.y - size.y + 2.0) / 28.0) * shape;
    float sweepG = local.x / size.x - fract(uTime * 0.075 + selected * 0.23);
    float sweep = exp(-sweepG * sweepG * 64.0) * shape * (0.035 + hover * 0.060 + selected * 0.070 + modeControl * 0.025);
    float shadowD = sdRoundBox(p - vec2(0.0, 15.0 + modePanel * 9.0), halfSize - vec2(3.0), uRadius);
    float shadow = (1.0 - smoothstep(0.0, mix(46.0, 76.0, modePanel), max(shadowD, 0.0))) * smoothstep(-22.0, 16.0, shadowD);
    vec3 color = accent * shadow * (0.040 + modePanel * 0.040 + selected * 0.035);
    color += glass * shape;
    color += vec3(grain * (0.006 + modePanel * 0.004)) * shape;
    color += accent * magnetic * (0.08 + glowGate * (0.10 + selected * 0.12 + hover * 0.12 + modeControl * 0.06));
    color += oklab_mix_srgb(vec3(1.0), accent, 0.34) * topRail * (0.11 + selected * 0.08);
    color += oklab_mix_srgb(vec3(1.0), accent, 0.44) * bevel * (0.024 + hover * 0.038 + selected * 0.035);
    color += oklab_mix_srgb(vec3(1.0), accent, 0.30) * sweep;
    color += oklab_mix_srgb(vec3(1.0), accent, 0.40) * rim * (0.045 + glowGate * (0.025 + hover * 0.070 + selected * 0.120 + modeControl * 0.040));
    color += vec3(1.0) * uFlash * shape * (0.16 + selected * 0.04);
    color -= vec3(0.020, 0.018, 0.023) * bottomShade * (0.45 - modePanel * 0.08);
    float alpha = max(shadow * (0.24 + modePanel * 0.12), shape * (0.66 + modePanel * 0.09 + modeOption * 0.03 + modeControl * 0.035) + onePx * (0.15 + selected * 0.05 + modeControl * 0.025) + magnetic * 0.055 + topRail * 0.034) * entry * quadFade;
    if (uLightMode > 0.5) {
        vec3 lightBase = oklab_mix_srgb(vec3(0.97, 0.985, 1.0), accent, 0.045 + selected * 0.090);
        color = lightBase * shape;
        color = mix(color, blurred, 0.14 + modePanel * 0.06);
        color += accent * magnetic * 0.060;
        color += oklab_mix_srgb(vec3(1.0), accent, 0.22) * rim * (0.050 + selected * 0.070);
        color += vec3(1.0, 0.88, 0.82) * uFlash * shape * 0.080;
        color -= vec3(0.050, 0.045, 0.048) * bottomShade * 0.11;
        alpha = max(shadow * 0.18, shape * (0.70 + modePanel * 0.05) + onePx * (0.080 + selected * 0.040) + magnetic * 0.024) * entry * quadFade;
    }
    if (alpha <= 0.002) {
        discard;
    }
    FragColor = vec4(color, clamp(alpha, 0.0, 1.0));
}
