#!/usr/bin/env python3
"""Monta a textura final (128x64) alinhando as pecas extras da skin fornecida
aos slots de UV do modelo ZollaGirl."""
from PIL import Image
import sys

SRC = sys.argv[1]   # 3.webp (hi-res)
OUT = sys.argv[2]   # destino zolla_girl.png

orig = Image.open(SRC).convert("RGBA")
OW, OH = orig.size
sx, sy = OW / 128.0, OH / 64.0

# base = skin deles reduzida a 128x64 (corpo/cabeca/bracos/pernas ja corretos)
base = orig.resize((128, 64), Image.LANCZOS)

def crop_src(x0, y0, x1, y1):
    return orig.crop((round(x0 * sx), round(y0 * sy), round(x1 * sx), round(y1 * sy)))

def place(src_box, dst_xywh):
    x0, y0, x1, y1 = src_box
    dx, dy, dw, dh = dst_xywh
    piece = crop_src(x0, y0, x1, y1).resize((dw, dh), Image.LANCZOS)
    base.paste(piece, (dx, dy))

# ---- ORELHAS  (slots do modelo: (64,0) e (64,8), bbox 10x6) ----
ear_src = (59, 0, 77, 12)
place(ear_src, (64, 0, 10, 6))
place(ear_src, (64, 8, 10, 6))

# ---- CAUDA  (slot (80,0), bbox 8x14) -> usa a mecha de cabelo creme ----
place((78, 1, 89, 14), (80, 0, 8, 14))

# ---- CABELO DAS COSTAS  (slot (64,20), bbox 20x23) ----
place((60, 18, 84, 44), (64, 20, 20, 23))

# ---- SAIA  (slot (96,40), bbox 28x11) ----
place((92, 38, 124, 50), (96, 40, 28, 11))

# ---- FRANJA  (slot (90,16), bbox 36x14) -> preenche com cor do cabelo ----
# (area livre; NAO sobrescreve as camadas de overlay da skin em 0,32/16,32/etc.)
hair_sample = crop_src(44, 2, 56, 12).resize((36, 14), Image.LANCZOS)
base.paste(hair_sample, (90, 16))

base.save(OUT)
print("textura final salva:", OUT, base.size)
