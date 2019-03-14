Loading Content Error for Mosby MVI.

Inspired by https://github.com/jshvarts/MosbyMVI

Usage:
1 Create ViewModel for Activity / Fragment / Layout
2 Create interface Exchange which push user Intent and receive LCE
3 Create Presenter which implements Exchange, and bind intent react From Repo and LCEListener
4 Handle onReduce in View Layer, and update view in onRender