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
        Java以外の言語のコーディングで使用。
      </>
    ),
  },
  {
    title: <>Eclipse</>,
    imageUrl: 'img/logo-icons/eclipse.svg',
    description: (
      <>
        Javaのコーディングで使用。
      </>
    ),
  },
   {
    title: <>Unity</>,
    imageUrl: 'img/logo-icons/unity.svg',
    description: (
      <>
        研修で使用。正直あまり覚えていない。
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
        普段の案件業務から、個人プロジェクトにわたり使用。差分はVSCodeなどGUIで確認し、コマンドはCLIで操作することが多い。
      </>
    ),
  },
  {
    title: <>Docker</>,
    imageUrl: 'img/logo-icons/docker-icon.svg',
    description: (
      <>
        実務使用経験あり。また、Dockerを使用した環境構築手順のQiita記事を書いた経験あり。
      </>
    ),
  }
];

const langAndframewarks = [
  {
    title: <>HTML</>,
    imageUrl: 'img/logo-icons/html-5.svg',
    description: (
      <>
        基本的には研修で学んだ程度。そのほか、FWのビューで記述したり、ReactのJSX（厳密には異なるが）で書いているなど。
      </>
    ),
  },
  {
    title: <>CSS</>,
    imageUrl: 'img/logo-icons/css-3.svg',
    description: (
      <>
        実務使用経験あり。
      </>
    ),
  },
  {
    title: <>JavaScript</>,
    imageUrl: 'img/logo-icons/javascript.svg',
    description: (
      <>
        実務使用経験ありであるが、基本的にフロントのみ。あとはGoogle Apps Scriptの経験あり。
      </>
    ),
  },
  {
    title: <>React</>,
    imageUrl: 'img/logo-icons/react.svg',
    description: (
      <>
        実務使用経験あり。自分のスキルのうち、フロントでメインにしたい技術。
      </>
    ),
  },
  {
    title: <>Docusaurus</>,
    imageUrl: 'img/logo-icons/docusaurus.svg',
    description: (
      <>
        当サイトで使用。v1は少々使いづらさを感じたので、v2を使用。
      </>
    ),
  },
  {
    title: <>Java</>,
    imageUrl: 'img/logo-icons/java.svg',
    description: (
      <>
        実務使用経験あり。この業界に入る前にメインで学んでいた言語で、自分のスキルのうち、バックエンドでメインにしたい言語。
      </>
    ),
  },
  {
    title: <>Spring</>,
    imageUrl: 'img/logo-icons/spring.svg',
    description: (
      <>
        実務使用経験あり。テンプレートエンジンは使用せず、APIとしての開発経験のみ。
      </>
    ),
  },
  {
    title: <>PHP</>,
    imageUrl: 'img/logo-icons/php.svg',
    description: (
      <>
        実務使用経験あり。個人勉強もしている言語。
      </>
    ),
  },
  {
    title: <>CakePHP</>,
    imageUrl: 'img/logo-icons/cakephp.svg',
    description: (
      <>
        実務使用経験あり。最初の案件で使用したFWであるため、ブランクあり。
      </>
    ),
  },
  {
    title: <>Laravel</>,
    imageUrl: 'img/logo-icons/laravel.svg',
    description: (
      <>
        個人勉強しているのみのFW。
      </>
    ),
  },
  {
    title: <>Python</>,
    imageUrl: 'img/logo-icons/python.svg',
    description: (
      <>
        実務使用経験あり。AWS LambdaをPythonで書いていた経験のみ。
      </>
    ),
  },
  {
    title: <>Ruby</>,
    imageUrl: 'img/logo-icons/ruby.svg',
    description: (
      <>
        研修で学んだ程度。
      </>
    ),
  },
  {
    title: <>Rails</>,
    imageUrl: 'img/logo-icons/rails.svg',
    description: (
      <>
        研修で学んだあと、Railsチュートリアルを学んだ程度。
      </>
    ),
  },
  {
    title: <>Jekyll</>,
    imageUrl: 'img/logo-icons/jekyll.svg',
    description: (
      <>
        自ブログで使用。テーマは公開されているものを使用しているが、それをカスタマイズして使用。
      </>
    ),
  },
  {
    title: <>C#</>,
    imageUrl: 'img/logo-icons/c-sharp.svg',
    description: (
      <>
        研修で学んだ程度。
      </>
    ),
  },
]

const careers = [
  {
    period: '2020/04 ～ 現在',
    title: '勤怠/入店管理システム',
    langAndFw: 'Ruby / Rails',
    db: 'MySQL',
    environment: 'Git / Docker / Circle Ci / Capistrano',
    other: '',
    description: '開発環境、ドキュメント整備。システムの機能改善。'
  },
  {
    period: '2019/09 ～ 2020/03',
    title: '売上/店舗/ユーザ管理システム',
    langAndFw: 'JavaScript / React / Java / Spring Boot',
    db: 'MySQL',
    environment: 'Git / Docker',
    other: 'Swagger',
    description: '実装部分を担当。メインはフロント担当であったものの、機能改修要件ではAPI側の修正も一緒に行なった時もあり。'
  },
  {
    period: '2019/06',
    title: '復職',
    langAndFw: '',
    db: '',
    environment: '',
    other: '',
    description: '通勤のリハビリをするところからはじまり、少しずつ勤務時間を伸ばして徐々に復帰。案件に入るまではReactの自学習。'
  },
  {
    period: '2019/02末頃 ～ 2019/05',
    title: '休職',
    langAndFw: '',
    db: '',
    environment: '',
    other: '',
    description: '大きく体調を崩して休職。心療内科もとい精神科で「適応障害」「不安障害」の診断がつく。'
  },
  {
    period: '2018/07 ～ 2019/02中頃',
    title: '宿泊予約システム',
    langAndFw: 'Java / Spring(+ Spring Boot) / Python',
    db: 'Amazon Aurora / Redis',
    environment: 'Git / Jenkins / Serverless Framework',
    other: '',
    description: '実装部分を担当。既存APIにキャッシュの導入、APIの新規作成、バッチ作成など。'
  },
  {
    period: '2018/01 ～ 2018/06',
    title: '保険請求システム',
    langAndFw: 'PHP / CakePHP',
    db: 'MySQL',
    environment: 'Git / Docker / Circle Ci / Deployer',
    other: '',
    description: '実装部分を担当。既存システムを元に、一から構築して作成。フロントとAPIとは切り離されておらず、CakePHPでどちらも対応。'
  },
  {
    period: '2017/10 ～ 2017/12',
    title: '入社後研修',
    langAndFw: 'C# / HTML / CSS / JavaScript / PHP / Ruby / Rails',
    db: 'MariaDB / SQLite',
    environment: 'Git',
    other: '',
    description: '未経験入社の社員がはじめに受講する研修。10月はC# + Unityでゲームを。11月はPHP、12月はRuby + Railsで日報システムを演習の成果物としてチームで作成。'
  },
  {
    period: '2017/10',
    title: '入社：フロイデ株式会社',
    langAndFw: '',
    db: '',
    environment: '',
    other: '',
    description: '独学でプログラミングを勉強し、全くの他業種から転職。C、C++と学んだあとはJavaの勉強に励み、入社日までにJava Goldを取得。'
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

function Career({period, title, langAndFw, db, environment, other, description}) {
  return (
    <div class="card-demo margin-vert--sm">
      <div class="card shadow--tl">
        <div class="card__header">
          <p className="text--italic">{period}</p>
          <h3>{title}</h3>
        </div>
        <div class="card__body">
          <ul>
            {langAndFw && <li>言語・FW：{langAndFw}</li>}
            {db && <li>DB：{db}</li>}
            {environment && <li>環境：{environment}</li>}
            {other && <li>その他：{other}</li>}
          </ul>
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
          <div className="avatar avatar--vertical">
            <img
              className={classnames('avatar__photo avatar__photo--xl', styles.avatar__photo_color)}
              src={useBaseUrl("img/lion-custom.svg")}
            />
            <div className="avatar__intro">
              <h2 className="avatar__name">Hitomi Yoshikawa</h2>
              <p className="avatar__subtitle">
                精神疾患持ちのプログラマー
                <br />
                JavaScirpt(React) + Java(Spring)をメインにしたい人
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
