const { screens } = require('tailwindcss/defaultTheme')

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./index.html', './src/**/*.{ts,tsx}'],
  theme: {
    extend: {
      colors: {
        'vtb-brand': {
          'DEFAULT': '#6ACCF6',
          'dark': '#0A1684'
        },
      }
    },
    screens: {
      ...screens,
      '2xl': '1460px' // To match swagger wrapper
    }
  },
  plugins: [],
}
