import React from 'react';
import classnames from 'classnames';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import useBaseUrl from '@docusaurus/useBaseUrl';
import styles from './styles.module.css';

const editors = [
  {
    title: <>Visual Studio Code</>,
    imageUrl: 'img/logo-icons/visual-studio-code.svg',
    description: (
      <>
        Java以外のコーディングで使用
      </>
    ),
  },
  {
    title: <>Eclipse</>,
    imageUrl: 'img/logo-icons/eclipse.svg',
    description: (
      <>
        Javaのコーディングで使用
      </>
    ),
  },
   {
    title: <>Unity</>,
    imageUrl: 'img/logo-icons/unity.svg',
    description: (
      <>
        研修で使用
      </>
    ),
  },
];

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

const careers = [
  {
    period: '2019/09 ～ 現在',
    title: '売上/店舗/ユーザ管理システム',
    langAndFw: 'JavaScript/React/Java/Spring Boot',
    db: 'MySQL',
    description: 'フロントとAPIを担当。'
  },
  {
    period: '2018/07 ～ 2019/02',
    title: '宿泊予約システム',
    langAndFw: 'Java/Spring/Python/Serverless Framework',
    db: 'Amazon Aurora',
    description: '新規API作成、既存APIの改修。'
  },
  {
    period: '2018/01 ～ 2018/06',
    title: '保険請求システム',
    langAndFw: 'PHP/CakePHP',
    db: 'MySQL',
    description: '実装を担当。'
  }
]

function Skill({imageUrl, title, description}) {
  const imgUrl = useBaseUrl(imageUrl);
  return (
    <div className={classnames('col col--3', styles.feature)}>
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

function Career({period, title, langAndFw, db, description}) {
  return (
    <div class="card-demo">
      <div class="card shadow--tl">
        <div class="card__header">
          <p className="text--italic">{period}</p>
          <h3>{title}</h3>
        </div>
        <div class="card__body">
          <p>言語・FW：{langAndFw}</p>
          <p>DB：{db}</p>
          <p>{description}</p>
        </div>
      </div>
    </div>
  )
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
          {/* <h1 className="hero__title">Hitomi Yoshikawa</h1>
          <p className="hero__subtitle">精神疾患持ちのプログラマー</p> */}
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
          <div className="avatar avatar--vertical">
            <img
              className={classnames('avatar__photo avatar__photo--xl', styles.avatar__photo_color)}
              src="img/lion-custom.png"
            />
            <div className="avatar__intro">
              <h2 className="avatar__name">Hitomi Yoshikawa</h2>
              <p className="avatar__subtitle">
                精神疾患持ちのプログラマー
              </p>
            </div>
          </div>
        </div>
      </header>
      <main>
        <div className="padding--md">
          <h2 className="text--center">スキル</h2>
          <h3 className={classnames('text--center', styles.decorationLine)}>エディタ</h3>
          {editors && editors.length && (
            <section className={styles.skills}>
              <div className="container">
                <div className="row">
                  {editors.map((props, idx) => (
                    <Skill key={idx} {...props} />
                  ))}
                </div>
              </div>
            </section>
          )}
          <h3 className={classnames('text--center', styles.decorationLine)}>開発環境</h3>
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
          <h3 className={classnames('text--center', styles.decorationLine)}>言語・フレームワーク</h3>
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
        </div>
        <div className="padding--md">
          <h2 className="text--center">経歴</h2>
          {careers && careers.length && (
            <section>
              <div className="container">
                {careers.map((props, idx) => (
                  <Career key={idx} {...props} />
                ))}
              </div>
            </section>
          )}
        </div>
      </main>
    </Layout>
  );
}

export default Portfolio;
