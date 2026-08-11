"""Generate PulseVisuals-style bodywear: braid-scythe mace."""
from __future__ import annotations

import base64
import json
import math
import struct
import zlib
from pathlib import Path

W = H = 64
ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / "src/main/resources/assets/wild/cosmetics/bodywear/138/model.json"
PREVIEW = ROOT / "_zip_inspect/mace_kosa_atlas.png"

# palette
K = (0, 0, 0, 0)
INK = (12, 10, 14, 255)
HAIR_D = (62, 38, 24, 255)
HAIR_M = (112, 68, 36, 255)
HAIR_L = (168, 108, 52, 255)
HAIR_H = (214, 168, 92, 255)
GOLD_D = (120, 78, 22, 255)
GOLD_M = (196, 142, 42, 255)
GOLD_L = (244, 208, 96, 255)
STEEL_D = (28, 30, 36, 255)
STEEL_M = (58, 64, 74, 255)
STEEL_L = (118, 128, 140, 255)
STEEL_H = (210, 218, 226, 255)
BONE = (232, 220, 196, 255)
RIB_D = (92, 18, 28, 255)
RIB_M = (156, 32, 44, 255)
RIB_L = (214, 64, 72, 255)
WRAP = (34, 24, 20, 255)


def new_img():
    return [[list(K) for _ in range(W)] for _ in range(H)]


def px(img, x, y, c):
    if 0 <= x < W and 0 <= y < H:
        img[y][x] = list(c)


def fill(img, x, y, w, h, c):
    for yy in range(y, y + h):
        for xx in range(x, x + w):
            px(img, xx, yy, c)


def shade_bar(img, x, y, w, h, colors):
    """Vertical cylinder shading: dark | mid | light | mid | dark."""
    n = len(colors)
    for xx in range(w):
        t = xx / max(1, w - 1)
        # highlight slightly left of center
        k = abs(t - 0.35)
        idx = min(n - 1, int(k * (n - 1) * 2.2))
        col = colors[idx]
        for yy in range(h):
            c = list(col)
            if yy % 4 == 0:
                c = [max(0, v - 18) for v in c[:3]] + [255]
            px(img, x + xx, y + yy, c)


def braid_tile(img, x, y, w, h, pal):
    for yy in range(h):
        phase = (yy // 2) % 3
        for xx in range(w):
            stripe = (xx + phase * 2) % 6
            if stripe < 2:
                c = pal[0]
            elif stripe < 4:
                c = pal[1]
            else:
                c = pal[2]
            if xx == 0 or xx == w - 1:
                c = pal[0]
            if xx == 1:
                c = pal[-1]
            px(img, x + xx, y + yy, c)


def paint_atlas(img):
    # 0,0  16x16 braid
    braid_tile(img, 0, 0, 16, 16, [HAIR_D, HAIR_M, HAIR_L, HAIR_H])
    # 16,0 16x16 braid highlight
    braid_tile(img, 16, 0, 16, 16, [HAIR_M, HAIR_L, HAIR_H, BONE])
    # 32,0 16x16 gold
    shade_bar(img, 32, 0, 16, 16, [GOLD_D, GOLD_M, GOLD_L, GOLD_M, GOLD_D])
    # 48,0 16x16 dark wrap / leather
    shade_bar(img, 48, 0, 16, 16, [INK, WRAP, HAIR_D, WRAP, INK])
    # 0,16 24x16 blade steel
    for yy in range(16):
        for xx in range(24):
            t = xx / 23
            if xx >= 20:
                c = STEEL_H if yy % 3 else BONE
            elif t < 0.2:
                c = STEEL_D
            elif t < 0.45:
                c = STEEL_M
            elif t < 0.7:
                c = STEEL_L
            else:
                c = STEEL_M
            if yy % 5 == 0:
                c = tuple(max(0, v - 20) for v in c[:3]) + (255,)
            px(img, xx, 16 + yy, c)
    # 24,16 16x16 ribbon
    shade_bar(img, 24, 16, 16, 16, [RIB_D, RIB_M, RIB_L, RIB_M, RIB_D])
    # 40,16 16x16 bone/edge
    shade_bar(img, 40, 16, 16, 16, [GOLD_D, BONE, STEEL_H, BONE, GOLD_D])
    # 56,16 8x16 studs
    fill(img, 56, 16, 8, 16, STEEL_D)
    for yy in range(16, 32, 4):
        fill(img, 58, yy + 1, 4, 2, STEEL_L)
        px(img, 59, yy + 1, STEEL_H)
    # 0,32 16x16 pommel
    shade_bar(img, 0, 32, 16, 16, [GOLD_D, GOLD_M, GOLD_L, GOLD_M, INK])
    # 16,32 16x16 mace flanges
    fill(img, 16, 32, 16, 16, STEEL_D)
    for i in range(4):
        fill(img, 16 + i * 4, 32, 3, 16, STEEL_M if i % 2 == 0 else STEEL_L)
        px(img, 17 + i * 4, 33, STEEL_H)
    # 32,32 unused dark
    fill(img, 32, 32, 32, 32, INK)


def png_bytes(img):
    raw = b"".join(b"\x00" + bytes(ch for pix in row for ch in pix) for row in img)

    def chunk(tag, data):
        crc = zlib.crc32(tag + data) & 0xFFFFFFFF
        return struct.pack(">I", len(data)) + tag + data + struct.pack(">I", crc)

    ihdr = struct.pack(">IIBBBBB", W, H, 8, 6, 0, 0, 0)
    return b"\x89PNG\r\n\x1a\n" + chunk(b"IHDR", ihdr) + chunk(b"IDAT", zlib.compress(raw, 9)) + chunk(b"IEND", b"")


def uv(u, v, su, sv):
    return {"uv": [u, v], "uv_size": [su, sv]}


def box(u, v, su=8, sv=8):
    return {
        "north": uv(u, v, su, sv),
        "east": uv(u + 1, v, max(1, su // 4), sv),
        "south": uv(u, v, su, sv),
        "west": uv(u + su - 2, v, max(1, su // 4), sv),
        "up": uv(u, v, su, max(1, sv // 4)),
        "down": uv(u, v + sv - 1, su, -max(1, sv // 4)),
    }


UV_BRAID = box(0, 0, 16, 16)
UV_BRAID_HI = box(16, 0, 16, 16)
UV_GOLD = box(32, 0, 16, 16)
UV_WRAP = box(48, 0, 16, 16)
UV_BLADE = box(0, 16, 24, 16)
UV_RIBBON = box(24, 16, 16, 16)
UV_BONE = box(40, 16, 16, 16)
UV_STUD = box(56, 16, 8, 16)
UV_POMMEL = box(0, 32, 16, 16)
UV_FLANGE = box(16, 32, 16, 16)


def cube(origin, size, pivot=None, rotation=None, inflate=None, uvs=None):
    c = {
        "origin": [round(v, 3) for v in origin],
        "size": [round(v, 3) for v in size],
        "uv": uvs or UV_BRAID,
    }
    if pivot is not None:
        c["pivot"] = [round(v, 3) for v in pivot]
    if rotation is not None:
        c["rotation"] = [round(v, 3) for v in rotation]
    if inflate is not None:
        c["inflate"] = inflate
    return c


def build_cubes():
    """Cubes in local space. Whole weapon tilt lives on the bone."""
    cubes = []
    z = 0.0

    # pommel at the bottom of a vertical shaft
    cubes.append(cube([-1.05, 0.0, z - 1.05], [2.1, 1.4, 2.1], uvs=UV_POMMEL))
    cubes.append(cube([-0.65, -0.7, z - 0.65], [1.3, 0.8, 1.3], uvs=UV_GOLD))

    # leather wrap
    cubes.append(cube([-0.8, 1.25, z - 0.8], [1.6, 3.4, 1.6], uvs=UV_WRAP))
    cubes.append(cube([-0.95, 1.7, z - 0.5], [1.9, 0.5, 1.0], [0, 1.95, z], [0, 28, 0], 0.01, UV_GOLD))
    cubes.append(cube([-0.95, 3.15, z - 0.5], [1.9, 0.5, 1.0], [0, 3.4, z], [0, -24, 0], 0.01, UV_GOLD))

    # braided shaft
    for i in range(8):
        y = 4.6 + i * 1.15
        side = 0.22 if i % 2 == 0 else -0.22
        uvs = UV_BRAID if i % 2 == 0 else UV_BRAID_HI
        cubes.append(cube([side - 0.8, y, z - 0.8], [1.6, 1.35, 1.6], [0, y + 0.67, z], [0, i * 18, 0], uvs=uvs))
        cubes.append(
            cube([side - 0.15, y + 0.15, z - 1.15], [0.9, 1.05, 0.9], [0, y + 0.67, z], [10, i * 18 + 45, 0], uvs=UV_BRAID_HI)
        )

    # collar + ribbon
    cubes.append(cube([-1.1, 13.7, z - 1.1], [2.2, 1.15, 2.2], uvs=UV_GOLD))
    cubes.append(cube([0.95, 13.85, z - 0.2], [1.85, 0.75, 0.4], [1.4, 14.2, z], [0, 0, -18], uvs=UV_RIBBON))
    cubes.append(cube([2.15, 12.85, z - 0.18], [0.6, 1.25, 0.36], [2.4, 13.9, z], [0, 0, 28], uvs=UV_RIBBON))
    cubes.append(cube([2.55, 12.7, z - 0.18], [0.6, 1.35, 0.36], [2.85, 13.85, z], [0, 0, -36], uvs=UV_RIBBON))

    # mace weight
    cubes.append(cube([-1.55, 14.7, z - 1.5], [3.1, 2.95, 3.0], uvs=UV_FLANGE))
    cubes.append(cube([1.5, 15.5, z - 0.4], [0.7, 0.9, 0.8], uvs=UV_STUD))
    cubes.append(cube([-2.2, 15.5, z - 0.4], [0.7, 0.9, 0.8], uvs=UV_STUD))
    cubes.append(cube([-0.4, 17.5, z - 0.4], [0.8, 0.65, 0.8], uvs=UV_GOLD))
    cubes.append(cube([-0.4, 15.55, z + 1.35], [0.8, 0.8, 0.65], uvs=UV_STUD))
    cubes.append(cube([-0.5, 15.05, z - 1.9], [1.05, 1.05, 0.5], uvs=UV_GOLD))

    # scythe crescent: plates chained from the mace head, each bent around its inner joint
    start_x, start_y = 1.45, 16.15
    plates = [
        (5.1, 1.40, 0.44, 16.0),
        (4.3, 1.18, 0.40, 38.0),
        (3.5, 0.98, 0.36, 60.0),
        (2.7, 0.78, 0.32, 82.0),
        (1.8, 0.56, 0.28, 102.0),
    ]
    for length, height, deep, ang in plates:
        origin = [start_x, start_y - height / 2, z - deep / 2]
        pivot = [start_x, start_y, z]
        cubes.append(cube(origin, [length, height, deep], pivot, [0, 0, ang], uvs=UV_BLADE))
        cubes.append(
            cube(
                [start_x + 0.08, start_y - height / 2 - 0.26, z - deep / 2 + 0.03],
                [length - 0.16, 0.28, deep - 0.06],
                pivot,
                [0, 0, ang],
                uvs=UV_BONE,
            )
        )
        rad = math.radians(ang)
        start_x += math.cos(rad) * (length - 0.15)
        start_y += math.sin(rad) * (length - 0.15)
    cubes.append(
        cube([start_x, start_y - 0.16, z - 0.1], [1.2, 0.32, 0.2], [start_x, start_y, z], [0, 0, 118], uvs=UV_BONE)
    )
    return cubes


PLAYER_BONES = [
    "head",
    "headwear",
    "body",
    "jacket",
    "left_arm",
    "left_sleeve",
    "right_arm",
    "right_sleeve",
    "left_leg",
    "left_pants",
    "right_leg",
    "right_pants",
]


def empty_model(name):
    return {
        "name": name,
        "color": 16777215,
        "visible": True,
        "pos": 0,
        "scale": 1,
        "x": 0,
        "y": 0,
        "z": 0,
        "yaw": 0,
        "pitch": 0,
        "roll": 0,
        "animations": [],
    }


def main():
    img = new_img()
    paint_atlas(img)
    png = png_bytes(img)
    PREVIEW.parent.mkdir(exist_ok=True)
    PREVIEW.write_bytes(png)

    cubes = build_cubes()
    pivots = {
        "head": [0, 24, 0],
        "headwear": [0, 24, 0],
        "body": [0, 24, 0],
        "jacket": [0, 24, 0],
        "left_arm": [5, 22, 0],
        "left_sleeve": [5, 22, 0],
        "right_arm": [-5, 22, 0],
        "right_sleeve": [-5, 22, 0],
        "left_leg": [2, 12, 0],
        "left_pants": [2, 12, 0],
        "right_leg": [-2, 12, 0],
        "right_pants": [-2, 12, 0],
    }
    bones = [
        {
            "name": "bb_main",
            "pivot": [0, 3.2, 0],
            "cubes": cubes,
        }
    ]
    for n, p in pivots.items():
        bones.append({"name": n, "pivot": p})

    model = {
        "format_version": "1.12.0",
        "minecraft:geometry": [
            {
                "description": {
                    "identifier": "geometry.wild_mace_kosa",
                    "texture_width": 64,
                    "texture_height": 64,
                    "visible_bounds_width": 3,
                    "visible_bounds_height": 3,
                    "visible_bounds_offset": [0, 0.75, 0],
                },
                "bones": bones,
            }
        ],
    }

    payload = {
        "name": "pulse_Mace Kosa",
        "id": 9038,
        "type": "bodywear",
        "category": 1,
        "author": "Wild",
        "pos": 4,
        "version": 1,
        "texture": base64.b64encode(png).decode("ascii"),
        "model": model,
        "models": [empty_model("bb_main"), *[empty_model(n) for n in pivots]],
        "config": [],
        "height": 0.1,
        "scale": 1,
        "x": 0,
        "y": -1.2,
        "z": 0,
        "yaw": 0,
        "pitch": 0,
        "roll": 0,
        "animations": [],
        "previewY": 0.2,
        "previewScale": 1.5,
    }

    OUT.parent.mkdir(parents=True, exist_ok=True)
    OUT.write_text(json.dumps(payload, ensure_ascii=False, separators=(",", ":")), encoding="utf-8")
    print(f"wrote {OUT} cubes={len(cubes)} png={len(png)}")


if __name__ == "__main__":
    main()
