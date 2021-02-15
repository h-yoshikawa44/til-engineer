module.exports = {
  title: 'TIL-Engineer',
  tagline: 'よしの勉強記録 & ポートフォリオ',
  url: 'https://h-yoshikawa0724-til-engineer.com',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  favicon: 'img/favicon.ico',
  // organizationName: 'facebook', // Usually your GitHub org/user name.
  projectName: 'til-engineer', // Usually your repo name.
  themeConfig: {
    prism: {
      additionalLanguages: ['docker', 'java', 'php'],
    },
    image: 'img/ogp.png',
    navbar: {
      title: 'よしの勉強記録',
      logo: {
        alt: '獅子のロゴアイコン',
        src: 'img/lion-logo.svg',
      },
      items: [
        {to: 'docs/top', label: 'Docs', position: 'left'},
        {to: '/skillandcareer', label: 'Skill & Career', position: 'left'},
        {to: '/myproducts', label: 'MyProducts', position: 'left'},
        {
          href: 'https://changeofpace.site',
          label: 'Blog',
          position: 'left',
        },
        {
          href: 'https://github.com/h-yoshikawa44/til-engineer',
          label: 'GitHub',
          position: 'right',
        },
      ],
    },
    footer: {
      style: 'dark',
      links: [
        {
          title: 'Site',
          items: [
            {
              label: 'Material Source',
              to: 'docs/source',
            },
            {
              label: 'Privacy Policy',
              to: 'docs/policy',
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
              href: 'https://github.com/h-yoshikawa44',
            },
            {
              label: 'Twitter',
              href: 'https://twitter.com/yoshi44_lion',
            },
            {
              label: 'Qiita',
              href: 'https://qiita.com/h-yoshikawa44',
            },
            {
              label: 'Zenn',
              href: 'https://zenn.dev/h_yoshikawa0724',
            },
            {
              html: `
                <a href="https://www.netlify.com" target="_blank" rel="noreferrer noopener" aria-label="Deploys by Netlify">
                  <img src="https://www.netlify.com/img/global/badges/netlify-color-accent.svg" alt="Deploys by Netlify" />
                </a>
              `,
            },
          ],
        },
      ],
      copyright: `Copyright © ${new Date().getFullYear()} Hitomi Yoshikawa Built with Docusaurus.`,
    },
    gtag: {
      trackingID: 'UA-141521257-2',
    },
  },
  presets: [
    [
      '@docusaurus/preset-classic',
      {
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          // editUrl:
          //   'https://github.com/h-yoshikawa44/til-engineer/edit/master/website/',
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
        sitemap: {
          cacheTime: 600 * 1000, // 600 sec - cache purge period
          changefreq: 'weekly',
          priority: 0.5,
        }
      },
    ],
  ],
};
