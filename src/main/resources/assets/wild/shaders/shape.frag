#version 330 core
in vec2 vLocalPx;
in vec2 vSize;
flat in vec4 vColorTL;
flat in vec4 vColorTR;
flat in vec4 vColorBR;
flat in vec4 vColorBL;
flat in vec4 vRadii;
flat in uint vFlags;
flat in ivec4 vClip;
flat in vec4 vClipRadii;
in vec2 vUV;
flat in vec2 vUvScale;
flat in vec2 vUvOffset;
flat in int vTexSlot;
in vec2 vPosPx;

uniform sampler2D uTextures[16];

out vec4 FragColor;

const float PI = 3.14159265;
const float TAU = 6.2831853;

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

vec4 oklab_mix_srgba(vec4 colA, vec4 colB, float t) {
    float k = clamp(t, 0.0, 1.0);
    return vec4(oklab_mix_srgb(colA.rgb, colB.rgb, k), mix(colA.a, colB.a, k));
}


vec4 sampleTexture(int slot, vec2 uv) {
    switch (slot) {
        case 0:  return texture(uTextures[0], uv);
        case 1:  return texture(uTextures[1], uv);
        case 2:  return texture(uTextures[2], uv);
        case 3:  return texture(uTextures[3], uv);
        case 4:  return texture(uTextures[4], uv);
        case 5:  return texture(uTextures[5], uv);
        case 6:  return texture(uTextures[6], uv);
        case 7:  return texture(uTextures[7], uv);
        case 8:  return texture(uTextures[8], uv);
        case 9:  return texture(uTextures[9], uv);
        case 10: return texture(uTextures[10], uv);
        case 11: return texture(uTextures[11], uv);
        case 12: return texture(uTextures[12], uv);
        case 13: return texture(uTextures[13], uv);
        case 14: return texture(uTextures[14], uv);
        case 15: return texture(uTextures[15], uv);
        default: return vec4(0.0);
    }
}

ivec2 textureDimensions(int slot) {
    switch (slot) {
        case 0:  return textureSize(uTextures[0], 0);
        case 1:  return textureSize(uTextures[1], 0);
        case 2:  return textureSize(uTextures[2], 0);
        case 3:  return textureSize(uTextures[3], 0);
        case 4:  return textureSize(uTextures[4], 0);
        case 5:  return textureSize(uTextures[5], 0);
        case 6:  return textureSize(uTextures[6], 0);
        case 7:  return textureSize(uTextures[7], 0);
        case 8:  return textureSize(uTextures[8], 0);
        case 9:  return textureSize(uTextures[9], 0);
        case 10: return textureSize(uTextures[10], 0);
        case 11: return textureSize(uTextures[11], 0);
        case 12: return textureSize(uTextures[12], 0);
        case 13: return textureSize(uTextures[13], 0);
        case 14: return textureSize(uTextures[14], 0);
        case 15: return textureSize(uTextures[15], 0);
        default: return ivec2(1, 1);
    }
}




float getRadius(vec2 p, vec4 r) {
    return (p.x > 0.0) ?
           ((p.y > 0.0) ? r.z : r.y) :
           ((p.y > 0.0) ? r.w : r.x);
}


float sdRoundBox(vec2 p, vec2 halfSize, vec4 radii) {
    vec4 safeRadii = min(radii, vec4(min(halfSize.x, halfSize.y)));
    float rad = getRadius(p, safeRadii);
    vec2 q = abs(p) - halfSize + rad;
    return min(max(q.x, q.y), 0.0) + length(max(q, vec2(0.0))) - rad;
}

float median(float r, float g, float b) {
    return max(min(r, g), min(max(r, g), b));
}


float getCoverage(float d) {
    float px = max(fwidth(d) * 0.7071, 0.0001);
    return 1.0 - smoothstep(-px, px, d);
}

void main() {
    uint mode = vFlags & 3u;
    float thickness = float((vFlags >> 2) & 0xFFu);
    float startRad = float((vFlags >> 10) & 0xFFu) / 255.0 * TAU;
    float arcPct = float((vFlags >> 18) & 0xFFu) / 255.0;

    float safeWx = (abs(vSize.x) > 1e-6) ? vSize.x : (vSize.x >= 0.0 ? 1e-6 : -1e-6);
    float safeHy = (abs(vSize.y) > 1e-6) ? vSize.y : (vSize.y >= 0.0 ? 1e-6 : -1e-6);
    vec2 gradUV = clamp(vLocalPx / vec2(safeWx, safeHy), 0.0, 1.0);

    vec4 col;
    if (all(equal(vColorTL, vColorTR)) && all(equal(vColorTL, vColorBR)) && all(equal(vColorTL, vColorBL))) {
        col = vColorTL;
    } else {
        vec4 colTop = oklab_mix_srgba(vColorTL, vColorTR, gradUV.x);
        vec4 colBottom = oklab_mix_srgba(vColorBL, vColorBR, gradUV.x);
        col = oklab_mix_srgba(colTop, colBottom, gradUV.y);
    }

    vec2 halfSize = 0.5 * vSize;
    vec2 p = vLocalPx - halfSize;


    if (vClip.z <= 0 || vClip.w <= 0) discard;
    if (vPosPx.x < float(vClip.x) || vPosPx.y < float(vClip.y) ||
        vPosPx.x >= float(vClip.x + vClip.z) || vPosPx.y >= float(vClip.y + vClip.w)) {
        discard;
    }

    float clipMask = 1.0;
    vec4 clipRadii = max(vClipRadii, vec4(0.0));
    if (clipRadii.x + clipRadii.y + clipRadii.z + clipRadii.w > 1e-6) {
        vec2 clipSize = vec2(float(vClip.z), float(vClip.w));
        if (clipSize.x <= 0.0 || clipSize.y <= 0.0) discard;
        vec2 clipHalf = clipSize * 0.5;
        vec2 clipCenter = vec2(float(vClip.x), float(vClip.y)) + clipHalf;
        float clipDistance = sdRoundBox(vPosPx - clipCenter, clipHalf, clipRadii);
        clipMask = getCoverage(clipDistance);
        if (clipMask <= 0.0) discard;
    }

    vec4 cornerRadii = max(vRadii, vec4(0.0));
    vec3 baseRgb = col.rgb;
    float baseAlpha = col.a;


    bool shadowMode = ((vFlags >> 26) & 0x1u) == 1u;
    if (shadowMode) {
        vec2 innerSizeRaw = vUvScale;
        vec2 resolvedInner = vec2(innerSizeRaw.x > 0.0 ? innerSizeRaw.x : vSize.x, innerSizeRaw.y > 0.0 ? innerSizeRaw.y : vSize.y);
        vec2 innerHalf = max(resolvedInner * 0.5, vec2(0.0));
        vec2 center = 0.5 * (vSize - resolvedInner) + innerHalf;

        float distInner = sdRoundBox(vLocalPx - center, innerHalf, cornerRadii);
        float blur = max(vUvOffset.x, 1e-3);
        float spread = max(vUvOffset.y, 0.0);

        float distFromSpread = max(distInner - spread, 0.0);
        float norm = distFromSpread / blur;
        float gaussian = exp(-0.5 * norm * norm);

        float limit = blur * 3.0;
        float outerMask = (limit > 0.0) ? (1.0 - smoothstep(limit - fwidth(distFromSpread), limit + fwidth(distFromSpread), distFromSpread)) : 1.0;
        float innerMask = clamp((distInner + fwidth(distInner)) / max(fwidth(distInner), 1e-4), 0.0, 1.0);

        float alpha = baseAlpha * gaussian * innerMask * outerMask * clipMask;
        if (alpha <= 0.001) discard;

        FragColor = vec4(baseRgb * alpha, alpha);
        return;
    }


    if (mode == 3u) {
        if (vTexSlot < 0 || vTexSlot >= 16) discard;

        bool isMsdf = ((vFlags >> 4) & 0x1u) == 1u;
        bool useScreenSpaceUv = ((vFlags >> 5) & 0x1u) == 1u;
        bool preservePremul = ((vFlags >> 6) & 0x1u) == 1u;

        vec2 sampleUv = useScreenSpaceUv ?
                        vec2(clamp(vPosPx.x * vUvScale.x + vUvOffset.x, 0.0, 1.0), clamp(vPosPx.y * vUvScale.y + vUvOffset.y, 0.0, 1.0)) :
                        vUV;

        vec4 tex = sampleTexture(vTexSlot, sampleUv);

        if (isMsdf) {
            float pxRange = max(cornerRadii.x, 1e-6);
            vec2 atlasSize = vec2(textureDimensions(vTexSlot));
            vec2 unitRange = vec2(pxRange) / max(atlasSize, vec2(1.0));
            float screenPxRange = max(0.5 * dot(unitRange, vec2(1.0) / max(fwidth(vUV), vec2(0.000001))), 1.0);

            float dist = median(tex.r, tex.g, tex.b);
            float opacity = clamp(screenPxRange * (dist - 0.5) + 0.5, 0.0, 1.0);
            float alpha = col.a * opacity;

            if (alpha <= 0.001) discard;
            FragColor = vec4(col.rgb * alpha, alpha);
            return;
        }


        float dS = sdRoundBox(p, halfSize, cornerRadii);
        float mask = getCoverage(dS);

        bool isRGBA = ((vFlags >> 2) & 0x1u) == 1u;
        bool forceOpaque = ((vFlags >> 3) & 0x1u) == 1u;

        vec4 sampled = isRGBA ? vec4(tex.rgb, forceOpaque ? 1.0 : tex.a) : vec4(vec3(pow(tex.r, 1.0/1.6)), tex.r);
        vec4 colTex = sampled * col;

        if (!preservePremul) colTex.rgb *= colTex.a;
        colTex *= mask * clipMask;

        if (colTex.a <= 0.001) discard;
        FragColor = colTex;
        return;
    }


    else if (mode == 2u) {
        float radius = halfSize.x;
        float dC = length(p) - radius;

        if (thickness > 0.0) {
            dC = abs(length(p) - (radius - thickness * 0.5)) - thickness * 0.5;
        }

        float aR = getCoverage(dC);
        float aA = 1.0;

        if (arcPct * TAU < TAU - 1e-6) {
            float ang = mod(atan(p.y, p.x) - startRad + TAU, TAU);
            float center = (arcPct * TAU) * 0.5;
            float delta = max(abs(ang - center) - center, 0.0);
            aA = getCoverage(radius * delta);
        }

        col.a *= aR * aA * clipMask;
    }


    else if (mode == 1u) {
        float dOuter = sdRoundBox(p, halfSize, cornerRadii);

        vec2 halfInner = max(halfSize - thickness, vec2(0.0));
        vec4 innerRadius = max(cornerRadii - thickness, vec4(0.0));
        float dInner = sdRoundBox(p, halfInner, innerRadius);

        float alphaOuter = getCoverage(dOuter);
        float alphaInner = getCoverage(dInner);

        col.a *= clamp(alphaOuter - alphaInner, 0.0, 1.0) * clipMask;
    }


    else {
        float dS = sdRoundBox(p, halfSize, cornerRadii);
        col.a *= getCoverage(dS) * clipMask;
    }

    col.rgb *= col.a;
    if (col.a <= 0.001) discard;
    FragColor = col;
}
