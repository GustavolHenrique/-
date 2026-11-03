# Guia de texturas — Zolla Girl

A entidade usa **uma única textura PNG de 128×64**:

```
src/main/resources/assets/zollacraft/textures/entity/zolla_girl.png
```

- **Metade esquerda (x 0–64)** = funciona como uma **skin normal de jogador**
  (cabeça, corpo, braços, pernas). Qualquer gerador de skin do Minecraft serve.
- **Metade direita (x 64–128)** = partes extras de catgirl
  (orelhas, cauda, cabelo, saia).

Veja o mapa visual em **`zolla_girl_uv_template.png`** (cada quadrado = 8 px).

---

## 📐 Mapa de UV exato

Cada caixa abaixo lista o **offset (u,v)** e o **tamanho (largura × altura × profundidade)**
do cubo. Na textura, a caixa ocupa um retângulo de largura `2·(w+d)` e altura `d+h`
a partir de `(u,v)` — é o "desdobramento" padrão do Minecraft (topo, baixo, 4 lados).

A **metade esquerda (0–64)** é uma **skin de jogador padrão 64×64** — inclusive
com a **segunda camada** (hat/jaqueta), que o modelo renderiza:

| Parte (base)     | offset (u,v) | tamanho (w×h×d) | 2ª camada (overlay) |
|------------------|--------------|-----------------|---------------------|
| Cabeça           | (0, 0)       | 8×8×8           | (32, 0) — cabelo    |
| Corpo / roupa    | (16, 16)     | 8×12×4          | (16, 32) — babados  |
| Braço direito    | (40, 16)     | 4×12×4          | (40, 32)            |
| Braço esquerdo   | (32, 48)     | 4×12×4          | (48, 48)            |
| Perna direita    | (0, 16)      | 4×12×4          | (0, 32)             |
| Perna esquerda   | (16, 48)     | 4×12×4          | (0, 48)             |

As **partes extras de catgirl** usam a metade direita:

| Parte extra      | offset (u,v) | tamanho (w×h×d) | observação                 |
|------------------|--------------|-----------------|----------------------------|
| Orelha direita   | (64, 0)      | 4×5×1           | ponta de gato              |
| Orelha esquerda  | (64, 8)      | 4×5×1           | espelhada                  |
| Cabelo das costas| (64, 20)     | 9×22×1          | cai até perto das pernas   |
| Cauda            | (80, 0)      | 2×12×2          | mecha de cabelo/cauda      |
| Franja (frente)  | (90, 16)     | 9×5×9           | cabelo por cima da cabeça  |
| Saia             | (96, 40)     | 9×6×5           | avental/saia               |

> A textura atual foi montada automaticamente a partir da skin que você forneceu
> (`compose.py`), recortando cada peça extra e encaixando nesses slots. Se trocar
> a skin base, rode o mesmo processo ou ajuste os offsets aqui e no modelo.

> O **rosto** fica na **face frontal da cabeça**: retângulo `x 8–16, y 8–16`.
> É ali que vão os olhos vermelhos e a boquinha.

---

## 🤖 Como pedir a textura pro ChatGPT (ou outro gerador)

1. Envie a imagem **`zolla_girl_uv_template.png`** como referência de layout.
2. Envie também sua imagem de referência da personagem (o desenho da catgirl).
3. Peça algo como:

   > "Esta é uma textura de skin do Minecraft, 128×64 pixels, estilo pixel art.
   > Preencha **cada caixa rotulada** com a parte correspondente da personagem
   > (cabelo preto, pele clara, olhos vermelhos, roupa branca com detalhes
   > vermelhos/laranja, orelhas e cauda de gato pretas, meias brancas).
   > Mantenha exatamente 128×64, sem suavização, cores chapadas por pixel,
   > fundo transparente nas áreas não usadas."

4. Salve o resultado **em 128×64 PNG** com o nome e caminho:
   `src/main/resources/assets/zollacraft/textures/entity/zolla_girl.png`
   (substituindo o placeholder).

5. (Opcional) Faça o mesmo para o ícone do item invocador (16×16):
   `src/main/resources/assets/zollacraft/textures/item/zolla_girl_spawn_egg.png`

> ⚠️ Geradores de imagem erram muito em pixel art precisa. Se o resultado sair
> "borrado" ou fora do tamanho, redimensione para 128×64 **sem** suavização
> (nearest neighbor) num editor como GIMP/Paint.NET/Aseprite, ou peça a textura
> dividida parte por parte.

---

## 🔧 Mudar o modelo

Se quiser mudar tamanhos/posições dos cubos, edite:
`src/main/java/com/zolla/zollacraft/entity/client/ZollaGirlModel.java`

Se adicionar/mudar caixas, **atualize os offsets de UV** aqui e no template
para a textura continuar batendo.
