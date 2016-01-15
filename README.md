功能
=======
在同名但扩展名不同的文件之间切换.

概念
=======
等价类型
--------
是指实现同一功能的不同扩展名, 如jade等价于html, coffee等价于js.

目前支持的对应关系为:

- html: jade, htm
- js: ts, coffee, jsx
- css: scss, less, styl
- test: test.js, test.coffee, test.jsx, spec.js, spec.coffee, spec.jsx

场景组
--------
- 内容组(Content): 用来处理页面的信息和交互, 在html/js之间切换
- 样式组(Style): 用来处理页面的外观, 在html/css之间切换
- 执行组(eXcute): 用来处理纯逻辑代码, 在js/test.js之间切换

用例
=========

靠最后一个文件就足够判断所属组
---------
css (NG) js test js test
test (NG) html css html css

要靠最近两个文件判断所属组
---------
html js html js (NG) css html css html
html js html (NG) css html css
html js (NG) html css html css

无法判断当前组时,默认找到当前文件所属的第一个组
---------
html (NG) js html js html (NG) css html css
js (NG) html js html (NG) css html css
test html (NG) js html js
css js (NG) html js html js

切换规则
========

cmd-L
--------
在正在编辑的文件和最近的同名文件之间切换, 如果历史中没有同名文件, 则切换到下一个扩展名

cmd-shift-L
--------
如果当前正在编辑内容组, 那么切换到样式组. 如果当前正在编辑样式组, 则切换到执行组. 如果正在编辑执行组, 则切换到内容组.

实现方式
========

获取当前组
--------
始终记录最后一次切到的组, 取出正在打开的和最近编辑的这两个文件, 判断它们是否处于这个组: 如果是, 则认为用户是在连续操作, c-s-l组合键将直接切换到下一个组, 否则根据最后的几个文件判断其处于哪个组

切换组
--------
获取当前组, 然后切换到下一个组去, 找到正在编辑的文件在那个组中的序号, 执行 "切换文件" 操作.

切换文件
--------
获取当前组, 找到正在编辑的文件在当前组中的序号, 找到下一个扩展名, 如果存在, 就打开
