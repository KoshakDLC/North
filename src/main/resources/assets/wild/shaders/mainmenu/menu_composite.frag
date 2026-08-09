#version 330 core

in vec2 vUv;
in vec2 vLocal;
in vec2 vScreen;

uniform sampler2D uTexture;
uniform vec2 uViewport;
uniform vec2 uTextureSize;
uniform vec2 uParallax;
uniform float uTime;
uniform float uEntry;
uniform float uClickFlash;
uniform float uLightMode;
uniform float uSakura;
uniform float uVernal;

out vec4 FragColor;

float hash12(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + vec3(33.33));
    return fract((p3.x + p3.y) * p3.z);
}

vec4 sampleScene(vec2 uv) {
    vec4 c = texture(uTexture, clamp(uv, vec2(0.0), vec2(1.0)));
    if (c.a > 0.0001 && c.a < 0.999) {
        c.rgb /= c.a;
    }
    return c;
}

vec3 blurSample(vec2 uv, float radius) {
    vec2 px = radius / max(uTextureSize, vec2(1.0));
    vec3 c = sampleScene(uv).rgb * 0.40;
    c += sampleScene(uv + vec2(px.x, 0.0)).rgb * 0.15;
    c += sampleScene(uv - vec2(px.x, 0.0)).rgb * 0.15;
    c += sampleScene(uv + vec2(0.0, px.y)).rgb * 0.15;
    c += sampleScene(uv - vec2(0.0, px.y)).rgb * 0.15;
    return c;
}

void main() {
    vec2 uv = vec2(vScreen.x / max(uViewport.x, 1.0), 1.0 - vScreen.y / max(uViewport.y, 1.0));
    vec2 centered = uv - 0.5;
    vec2 warped = uv + uParallax + centered * dot(centered, centered) * 0.0014;
    float entry = smoothstep(0.0, 1.0, uEntry);
    float focus = 1.0 - entry;
    float sakura = clamp(uSakura, 0.0, 1.0);
    float vernal = clamp(uVernal, 0.0, 1.0);
    vec3 sharp = sampleScene(warped).rgb;
    vec3 soft = blurSample(warped, 9.0 * focus + sakura * 2.2 + vernal * 1.35);
    vec3 color = mix(soft, sharp, mix(entry, entry * 0.85, max(sakura, vernal * 0.72)));
    float aspect2d = uViewport.x / max(uViewport.y, 1.0);
    float vignette = 1.0 - smoothstep(0.16, 1.06, length(centered * vec2(aspect2d, 1.0)));
    float grain = hash12(vScreen + fract(uTime) * 8192.0) - 0.5;
    if (uLightMode > 0.5) {
        float vigStr = 0.025 + sakura * 0.060 + vernal * 0.024;
        color *= (1.0 - vigStr) + vignette * vigStr;
        color += mix(vec3(1.0, 0.88, 0.80), vec3(1.0, 0.95, 0.60), vernal) * uClickFlash * (0.030 + vernal * 0.020) * smoothstep(0.0, 0.44, entry);
        color += grain * vec3(0.0022);
    } else {
        color *= 0.86 + vignette * 0.14;
        color += vec3(0.055, 0.058, 0.066) * uClickFlash * smoothstep(0.0, 0.44, entry);
        color += grain * vec3(0.0048);
    }
    color *= smoothstep(0.0, 0.84, uEntry);
    FragColor = vec4(color, 1.0);
}
