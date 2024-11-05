import react from '@vitejs/plugin-react';
import { defineConfig } from 'vite';

// eslint-disable-next-line no-restricted-exports
export default defineConfig({
  plugins: [react()],
  build: {
    outDir: 'build',
  },
  server: {
    port: 3000,
  },
  define: {
    'process.env': process.env,
  },
});
