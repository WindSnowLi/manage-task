(function ($) {
    var pages = {
        init: function (obj, page) {
            return (function () {
                pages.addHtml(obj, page);
                pages.bindClick(obj, page);
            }());
        },
        addHtml: function (obj, page) {
            return (function () {
                // 移除默认的节点
                obj.empty();
                //判断总页数是否大于1
                if (page.pageNum > 1) {
                    obj.append('<a href="javascript:;" class="preBtn">上一页</a>')
                } else {
                    obj.remove('.prevPage');
                    obj.append('<a href="javascript:;" class="disabled">上一页</a>')
                }

                // 中间页哪里用省略号...替代
                if (page.current > 4 && page.pageNum > 4) {                     //页数和总页数都大于4
                    obj.append('<a href="javascript:;" class="pageNum">1</a>')
                    obj.append('<a href="javascript:;" class="pageNum">2</a>')
                    obj.append('<span>...</span>')
                }
                if (page.current > 4 && page.current <= page.pageNum - 5) {
                    var start = page.current - 2;
                    var end = page.current + 2;
                } else if (page.current > 4 && page.current > page.pageNum - 5) {
                    var start = page.pageNum - 4;
                    var end = page.pageNum;
                } else {
                    var start = 1;
                    var end = 9;
                }

                // 遍历start到end为页数渲染结构
                for (; start <= end; start++) {
                    if (start <= page.pageNum && start >= 1) {
                        if (start == page.current) {
                            obj.append('<span class="current">' + start + '</span>');
                        } else if (start == page.current + 1) {
                            obj.append('<a href="javascript:;" class="pageNum nextPage">' + start + '</a>');
                        } else {
                            obj.append('<a href="javascript:;" class="pageNum">' + start + '</a>');
                        }
                    }
                }

                //最后部分
                if (end < page.pageNum) {
                    obj.append('<span>...</span>');
                }

                /*下一页*/
                if (page.current >= page.pageNum) {
                    obj.remove('.nextBtn');
                    obj.append('<span class="disabled">下一页</span>');
                } else {
                    obj.append('<a href="javascript:;" class="nextBtn">下一页</a>');
                }

                //输入跳转部分
                obj.append('<span>' + '共' + '<b>' + page.pageNum + '</b>' + '页，' + '</span>');
                obj.append('<span>' + '到第' + '<input type="number" class="pageInput" value="1"/>' + '页' + '</span>');
                obj.append('<span class="changePage">' + '确定' + '</span>');

            }())
        },
        bindClick: function (obj, page) {
            return (function () {
                //绑定上一页的点击事件
                obj.on("click", ".preBtn", function () {
                    var cur = parseInt(obj.children("span.current").text());
                    var current = $.extend(page, { "current": cur - 1 });
                    pages.addHtml(obj, current);
                    if (typeof (page.backfun) == "function") {
                        page.backfun(current);
                    }
                });
                //绑定数字按钮的点击事件
                obj.on("click", ".pageNum", function () {
                    var cur = parseInt($(this).text());
                    var current = $.extend(page, { "current": cur });
                    pages.addHtml(obj, current);
                    if (typeof (page.backfun) == "function") {
                        page.backfun(current);
                    }
                });
                //绑定下一页的点击事件
                obj.on("click", ".nextBtn", function () {
                    var cur = parseInt(obj.children("span.current").text());
                    var current = $.extend(page, { "current": cur + 1 });
                    pages.addHtml(obj, current);
                    if (typeof (page.backfun) == "function") {
                        page.backfun(current);
                    }
                });

                //绑定确定按钮的点击事件
                obj.on("click", ".changePage", function () {
                    var current = $.extend(page, { "current": cur });
                    var cur = parseInt($("input.pageInput").val());
                    if (cur > 0 || cur <= page.pageNum) {
                        pages.addHtml(obj, { "current": cur, "pageNum": page.pageNum });
                    } else {
                        pages.addHtml(obj, current);
                    }
                    if (typeof (page.backfun) == "function") {
                        page.backfun(current);
                    }
                });

            }())
        }
    }
    $.fn.createPage = function (options) {
        var page = $.extend({
            pageNum: 15,
            current: 1,
            backfun: function () { }
        }, options);
        pages.init(this, page)
    }
}(jQuery));