import js from '@eslint/js'
import pluginVue from 'eslint-plugin-vue'
import tseslint from 'typescript-eslint'
import vueParser from 'vue-eslint-parser'

export default [
  {
    name: 'app/files-to-ignore',
    ignores: ['**/dist/**', '**/dist-ssr/**', '**/coverage/**', '**/node_modules/**'],
  },

  js.configs.recommended,
  ...pluginVue.configs['flat/essential'],
  {
    files: ['**/*.{ts,tsx,vue}'],
    languageOptions: {
      parserOptions: { ecmaVersion: 'latest', sourceType: 'module' },
    },
    plugins: {
      '@typescript-eslint': tseslint.plugin,
    },
    rules: {
      ...tseslint.configs.recommended[0].rules,
      ...tseslint.configs.recommended[1].rules,
    },
  },

  {
    files: ['**/*.{ts,tsx,js,jsx,vue}'],
    languageOptions: {
      globals: {
        console: 'readonly',
        localStorage: 'readonly',
        location: 'readonly',
        ElTable: 'readonly',
        ElMessage: 'readonly',
        ElMessageBox: 'readonly',
        ElNotification: 'readonly',
      },
    },
  },

  {
    files: ['**/*.vue'],
    languageOptions: {
      parser: vueParser,
      parserOptions: {
        parser: tseslint.parser,
        ecmaVersion: 'latest',
        sourceType: 'module',
      },
    },
  },

  {
    files: ['**/*.{ts,tsx,js,jsx}'],
    languageOptions: {
      parser: tseslint.parser,
      parserOptions: {
        ecmaVersion: 'latest',
        sourceType: 'module',
      },
    },
  },

  {
    files: ['**/*.{ts,tsx,js,jsx,vue}'],
    rules: {
      'no-console': 'off',
      'no-undef': 'off',
      'vue/multi-word-component-names': 'off',
      'no-unused-vars': 'off',
    },
  },

  {
    files: ['**/*.{ts,tsx,vue}'],
    rules: {
      '@typescript-eslint/no-explicit-any': 'warn',
      '@typescript-eslint/no-unused-vars': ['warn', { argsIgnorePattern: '^_' }],
    },
  },
]
