Rails.application.routes.draw do
  root 'static_pages#home'
  get 'foo/bar'
  get 'foo/baz'
  get 'static_pages/home'
  get 'static_pages/help'
  get 'static_pages/about'
  resources :microposts
  resources :users
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end