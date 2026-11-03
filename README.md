# Zolla Craft

Mod para **Minecraft 1.16.5 (Forge)** que adiciona garotas de anime estilo *catgirl*,
feitas de cubos, como **aliadas** que você pode domesticar e que lutam ao seu lado.

> Inspirado na estética de catgirl (cabelo preto longo, orelhas e cauda de gato,
> roupa branca com detalhes vermelhos/laranja), reconstruída em blocos no estilo
> do Minecraft.

---

## ✨ O que o mod faz (v1.0.0)

- **Entidade "Zolla Girl"** — modelo 3D cúbico com:
  - cabeça, corpo, braços e pernas (estilo skin de jogador)
  - orelhas de gato, cauda que balança, cabelo longo nas costas, franja e saia
  - animações de caminhada, cauda e orelhas, e **pose sentada**
- **Aliada domesticável**:
  - domestique com **peixe cru (bacalhau / cod)** — chance de 1 em 3 por peixe
  - depois de domesticada: **segue você**, **senta** (clique direito sem item),
    **defende você** atacando monstros próximos, e pode ser **curada com comida**
- **Item invocador** ("Ovo de Spawn: Garota Zolla") na aba *Diversos* do criativo —
  clique com o botão direito num bloco para invocar.
- Sons reaproveitados do gato vanilla.
- Idiomas: Inglês (`en_us`) e Português do Brasil (`pt_br`).

---

## 🔨 Como compilar (no seu PC)

> ⚠️ Este repositório **não foi compilado no ambiente em nuvem** porque o
> servidor de dependências do Forge (`maven.minecraftforge.net`) está bloqueado
> pela política de rede de lá. No **seu computador** isso funciona normalmente.

**Pré-requisitos:** Java **8** (JDK 8) instalado.

```bash
# Linux / macOS
./gradlew build

# Windows
gradlew.bat build
```

O `.jar` final sai em `build/libs/zollacraft-1.0.0.jar`.
Copie esse arquivo para a pasta `mods/` de um perfil **Forge 1.16.5**.

### Rodar no ambiente de teste (opcional, para desenvolvimento)

```bash
./gradlew runClient
```

---

## 🧪 Como usar no jogo

1. Instale o Forge **1.16.5** (versão 36.x).
2. Coloque `zollacraft-1.0.0.jar` em `.minecraft/mods/`.
3. No criativo, pegue o **Ovo de Spawn: Garota Zolla** (aba *Diversos*).
4. Clique com o botão direito no chão para invocar.
5. Com **peixe cru** na mão, clique nela até aparecerem corações (domesticada).
6. Clique direito (sem item) para ela **sentar/levantar**. Ela segue e defende você.

---

## 🎨 Texturas (importante!)

A textura atual (`zolla_girl.png`, 128×64) é um **placeholder** simples, só pra
funcionar de cara. Para deixar bonita (ex.: pedir pro ChatGPT gerar), use o
**mapa de UV** incluído:

- 🖼️ Template visual: [`docs/zolla_girl_uv_template.png`](docs/zolla_girl_uv_template.png)
- 📄 Guia detalhado: [`docs/README-TEXTURAS.md`](docs/README-TEXTURAS.md)

Basta pintar/gerar **dentro das caixas** marcadas no template e salvar como
`src/main/resources/assets/zollacraft/textures/entity/zolla_girl.png` (128×64, PNG).

---

## 📁 Estrutura

```
src/main/java/com/zolla/zollacraft/
├── ZollaCraft.java              # classe principal do mod
├── init/ModEntityTypes.java     # registro da entidade
├── init/ModItems.java           # registro do item invocador
├── entity/custom/ZollaGirlEntity.java   # lógica (domesticar, seguir, lutar)
├── entity/client/ZollaGirlModel.java    # modelo 3D de cubos
├── entity/client/ZollaGirlRenderer.java # renderização
├── item/ZollaSpawnItem.java     # item que invoca a entidade
├── event/ModEventBusEvents.java # atributos da entidade
└── client/ClientSetup.java      # registro do renderer

src/main/resources/
├── META-INF/mods.toml
├── pack.mcmeta
├── assets/zollacraft/...        # texturas, modelos de item, lang
└── data/zollacraft/...          # loot table
```

---

## 🗺️ Próximos passos (ideias)

- Variantes (cores de cabelo/roupa diferentes) via data param + múltiplas texturas
- Inventário/equipamento próprio da aliada
- Reprodução entre duas Zolla Girls domesticadas
- Spawn natural em biomas específicos

Pull requests e ideias são bem-vindos. 🐾
