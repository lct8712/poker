poker
=====
豌豆德州

# 数据展示
- 支持手机浏览器

# 查询
- 总榜、时间段、最近次数
- 总和、平均数、方差
- 支持按名字搜索

# 新增数据
- 直接上传文件
- 表单提交

# 数据存储
- 文件的形式
- 每次的数据是一个文件

# 接口
- http://localhost:8080/api/game/all?season=1

- /api/player/all?season=1
- /api/player/search/{username}?season=1
- /api/player/ranking?type={sum,count,mean,stddev}?season=1

- /api/game/all?season=1
- /api/game/search/{date}?season=1
- /api/game/update

- /api/season/list
- /api/season/update

- /api/reload
- /api/zip

# 页面
http://localhost:8080/index.html
