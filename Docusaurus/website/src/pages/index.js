import React from 'react';
import classnames from 'classnames';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import useBaseUrl from '@docusaurus/useBaseUrl';
import styles from './styles.module.css';

function Home() {
  const context = useDocusaurusContext();
  const {siteConfig = {}} = context;
  return (
    <Layout
      title={`${siteConfig.title} from h-yoshikawa`}
      description="よしの勉強記録 & ポートフォリオ サイト">
      <header className={classnames('hero hero--primary', styles.heroBanner)}>
        <div className="container">
          <h1 className="hero__title">{siteConfig.title}</h1>
          <p className="hero__subtitle">{siteConfig.tagline}</p>
          <div className={styles.buttons}>
            <Link
              className={classnames(
                'button button--outline button--info button--lg margin--xs',
                styles.getStarted,
              )}
              to={useBaseUrl('docs/top')}>
              勉強記録
            </Link>
            <Link
              className={classnames(
                'button button--outline button--info button--lg margin--xs',
                styles.getStarted,
              )}
              to={useBaseUrl('/portfolio')}>
              ポートフォリオ
            </Link>
          </div>
        </div>
      </header>
      <main>
        <div className="padding--md">
          <h2 className="text--center">TIL</h2>
          <h3 className={classnames('text--center', styles.decorationLine)}>TILとは？</h3>
          <section>
            <div className="container">
              <blockquote>「Today I Learned」の略で、Github上にTILというリポジトリを作成してそこに今日覚えたことを書いていくというものです。</blockquote>
              <p className="text--right">
                出典：
                <Link to="https://qiita.com/nemui_/items/239335b4ed0c3c797add">
                  Qiita - Githubのリポジトリ「TIL」を使って小さなアウトプットを習慣化する
                </Link>
              </p>
            </div>
          </section>
          <h3 className={classnames('text--center', styles.decorationLine)}>目的</h3>
          <section>
          　<div className="container">
              <p>勉強したコードやメモなどをTILリポジトリにあげていく。またここにドキュメントとしてまとめる。</p>
              <ul>
                <li>アウトプットの習慣が身につく</li>
                <li>勉強したことを可視化することでモチベーションに繋げる</li>
                <li>他の人に勉強した内容を伝えられる材料になる</li>
              </ul>
              <p>すでにリポジトリにあげているコードやメモは順次ドキュメント化中。</p>
            </div>
          </section>
        </div>
      </main>
    </Layout>
  );
}

export default Home;
