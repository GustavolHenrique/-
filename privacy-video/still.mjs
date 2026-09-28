// Exports a static 1254px HTML post to PNG at a given output size.
// Usage: node still.mjs <page.html> <out.png> [size=1080]
import { createRequire } from 'module';
import path from 'path';

const require = createRequire(import.meta.url);
const { chromium } = require(process.env.PLAYWRIGHT_PATH || '/opt/node22/lib/node_modules/playwright');

const DIR = path.dirname(new URL(import.meta.url).pathname);
const [src, out, size = '1080'] = process.argv.slice(2);
const browser = await chromium.launch();
const page = await browser.newPage({ viewport: { width: 1254, height: 1254 }, deviceScaleFactor: Number(size) / 1254 });
await page.goto('file://' + path.resolve(DIR, src));
await page.evaluate(() => window.ready);
await page.screenshot({ path: path.resolve(DIR, out) });
await browser.close();
