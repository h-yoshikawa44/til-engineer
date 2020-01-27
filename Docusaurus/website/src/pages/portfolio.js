import React from 'react';
import classnames from 'classnames';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import useBaseUrl from '@docusaurus/useBaseUrl';
import styles from './styles.module.css';

const environments = [
  {
    title: <>Git</>,
    imageUrl: 'img/logo-icons/git-icon.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  },
  {
    title: <>Docker</>,
    imageUrl: 'img/logo-icons/docker-icon.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  }
];

const langAndframewarks = [
  {
    title: <>JavaScript</>,
    imageUrl: 'img/logo-icons/javascript.svg',
    description: (
      <>
        実務経験あり（※フロントエンド）
      </>
    ),
  },
  {
    title: <>React</>,
    imageUrl: 'img/logo-icons/react.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  },
  {
    title: <>Docusaurus</>,
    imageUrl: 'img/logo-icons/docusaurus.svg',
    description: (
      <>
        当サイトで使用
      </>
    ),
  },
  {
    title: <>Java</>,
    imageUrl: 'img/logo-icons/java.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  },
  {
    title: <>Spring</>,
    imageUrl: 'img/logo-icons/spring.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  },
  {
    title: <>PHP</>,
    imageUrl: 'img/logo-icons/php.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  },
  {
    title: <>CakePHP</>,
    imageUrl: 'img/logo-icons/cakephp.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  },
  {
    title: <>Laravel</>,
    imageUrl: 'img/logo-icons/laravel.svg',
    description: (
      <>
        自己学習
      </>
    ),
  },
  {
    title: <>Python</>,
    imageUrl: 'img/logo-icons/python.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  },
  {
    title: <>Ruby</>,
    imageUrl: 'img/logo-icons/ruby.svg',
    description: (
      <>
        研修
      </>
    ),
  },
  {
    title: <>Rails</>,
    imageUrl: 'img/logo-icons/rails.svg',
    description: (
      <>
        研修
      </>
    ),
  },
  {
    title: <>Jekyll</>,
    imageUrl: 'img/logo-icons/jekyll.svg',
    description: (
      <>
        自ブログで使用
      </>
    ),
  },
  {
    title: <>HTML</>,
    imageUrl: 'img/logo-icons/html-5.svg',
    description: (
      <>
        研修
      </>
    ),
  },
  {
    title: <>CSS</>,
    imageUrl: 'img/logo-icons/css-3.svg',
    description: (
      <>
        実務経験あり
      </>
    ),
  },
  {
    title: <>C#</>,
    imageUrl: 'img/logo-icons/c-sharp.svg',
    description: (
      <>
        研修
      </>
    ),
  },
]

function Skill({imageUrl, title, description}) {
  const imgUrl = useBaseUrl(imageUrl);
  return (
    <div className={classnames('col col--4', styles.feature)}>
      {imgUrl && (
        <div className="text--center">
          <img className={styles.skillImage} src={imgUrl} alt={title} />
        </div>
      )}
      <h4 className="text--center">{title}</h4>
      <p className="text--center">{description}</p>
    </div>
  );
}

function Portfolio() {
  const context = useDocusaurusContext();
  const {siteConfig = {}} = context;
  return (
    <Layout
      title={'portfolio from h-yoshikawa'}
      description="よしのポートフォリオ">
      <header className={classnames('hero hero--primary', styles.heroBanner)}>
        <div className="container">
          <h1 className="hero__title">Hitomi Yoshikawa</h1>
          <p className="hero__subtitle">精神疾患持ちのプログラマー</p>
          {/* <div className={styles.buttons}>
            <Link
              className={classnames(
                'button button--outline button--secondary button--lg',
                styles.getStarted,
              )}
              to={useBaseUrl('docs/doc1')}>
              勉強記録
            </Link>
          </div> */}
        </div>
      </header>
      <main>
        <h2>スキル</h2>
        <h3>開発環境</h3>
        {environments && environments.length && (
          <section className={styles.skills}>
            <div className="container">
              <div className="row">
                {environments.map((props, idx) => (
                  <Skill key={idx} {...props} />
                ))}
              </div>
            </div>
          </section>
        )}
        <h3>言語・フレームワーク</h3>
        {langAndframewarks && langAndframewarks.length && (
          <section className={styles.skills}>
            <div className="container">
              <div className="row">
                {langAndframewarks.map((props, idx) => (
                  <Skill key={idx} {...props} />
                ))}
              </div>
            </div>
          </section>
        )}
      </main>
    </Layout>
  );
}

export default Portfolio;
