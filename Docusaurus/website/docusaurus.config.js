module.exports = {
  title: 'TIL-Engineer',
  tagline: 'よしの勉強記録 & ポートフォリオ',
  url: 'https://h-yoshikawa0724-til.netlify.com',
  baseUrl: '/',
  favicon: 'img/favicon.ico',
  // organizationName: 'facebook', // Usually your GitHub org/user name.
  projectName: 'TIL-Engineer', // Usually your repo name.
  themeConfig: {
    navbar: {
      title: 'よしの勉強記録',
      logo: {
        alt: '獅子のロゴアイコン',
        src: 'img/lion-logo.svg',
      },
      links: [
        {to: 'docs/doc1', label: 'Docs', position: 'left'},
        {to: '/portfolio', label: 'Portfolio', position: 'left'},
        {
          href: 'https://changeofpace.site',
          label: 'Blog',
          position: 'left',
        },
        {
          href: 'https://github.com/h-yoshikawa0724/TIL-Engineer',
          label: 'GitHub',
          position: 'right',
        },
      ],
    },
    footer: {
      style: 'dark',
      links: [
        // {
        //   title: 'Docs',
        //   items: [
        //     {
        //       label: 'Style Guide',
        //       to: 'docs/doc1',
        //     },
        //     {
        //       label: 'Second Doc',
        //       to: 'docs/doc2',
        //     },
        //   ],
        // },
        {
          title: 'Site',
          items: [
            {
              label: 'Material Source',
              to: 'docs/source',
            }
          ],
        },
        {
          title: 'Social',
          items: [
            {
              label: 'Blog',
              href: 'https://changeofpace.site',
            },
            {
              label: 'GitHub',
              href: 'https://github.com/h-yoshikawa0724/TIL-Engineer',
            },
            {
              label: 'Twitter',
              href: 'https://twitter.com/yoshi0724_lion',
            },
          ],
        },
      ],
      copyright: `Copyright © ${new Date().getFullYear()} Hitomi Yoshikawa Built with Docusaurus.`,
    },
  },
  presets: [
    [
      '@docusaurus/preset-classic',
      {
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          // editUrl:
          //   'https://github.com/h-yoshikawa0724/TIL-Engineer/edit/master/website/',
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      },
    ],
  ],
};
