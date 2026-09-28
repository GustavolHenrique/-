// Renders scene.html frame-by-frame and encodes an MP4.
// Usage: node render.mjs [--still t1,t2,...]
import { createRequire } from 'module';
import { execFileSync } from 'child_process';
import fs from 'fs';
import path from 'path';

const require = createRequire(import.meta.url);
const { chromium } = require(process.env.PLAYWRIGHT_PATH || '/opt/node22/lib/node_modules/playwright');

const DIR = path.dirname(new URL(import.meta.url).pathname);
const FPS = 60, DURATION = 5, SIZE = 1254;
const FFMPEG = process.env.FFMPEG || 'ffmpeg';
const frameDir = path.join(DIR, 'frames');

const browser = await chromium.launch();
const page = await browser.newPage({ viewport: { width: SIZE, height: SIZE } });
await page.goto('file://' + path.join(DIR, 'scene.html'));
await page.evaluate(() => window.ready);

const stillArg = process.argv.indexOf('--still');
if (stillArg > -1) {
  for (const t of process.argv[stillArg + 1].split(',').map(Number)) {
    await page.evaluate(t => window.render(t, 0), t);
    await page.screenshot({ path: path.join(DIR, `still-${t}.png`) });
  }
  await browser.close();
  process.exit(0);
}

fs.rmSync(frameDir, { recursive: true, force: true });
fs.mkdirSync(frameDir);
const total = FPS * DURATION;
for (let f = 0; f < total; f++) {
  await page.evaluate(([t, f]) => window.render(t, f), [f / FPS, f]);
  await page.screenshot({ path: path.join(frameDir, `f${String(f).padStart(4, '0')}.png`) });
}
await browser.close();

// 60fps render -> 30fps with 2-frame blend for natural motion blur, downscaled to 1080x1080.
execFileSync(FFMPEG, ['-y', '-framerate', String(FPS), '-i', path.join(frameDir, 'f%04d.png'),
  '-vf', 'tmix=frames=2,framestep=2,scale=1080:1080:flags=lanczos,format=yuv420p',
  '-r', '30', '-c:v', 'libx264', '-preset', 'slow', '-crf', '16', '-movflags', '+faststart',
  path.join(DIR, 'privacy-consent.mp4')], { stdio: 'inherit' });
