var page = 0;
var searchBy = 'document';
var firstSearch = document.getElementById('firstSearch');
const overlay = document.getElementById('overlay');

function loadMoreData() {
    $.ajax({
        url: '/api/student/search',
        type: 'GET',
        data: {
            skip: page,
            search: searchKeyword
        },
        success: function (data) {
            page++;
            appendDataToResults(data);
            highlightSearchResults(); // Thực hiện highlight sau khi thêm dữ liệu
        },
        error: function () {
            console.log("false")
        }
    });
}

function search(event) {
    overlay.style.display = 'block';
    firstSearch.style.display = 'none';
    event.preventDefault(); // Chặn hành động submit mặc định của form
    let searchKeyword = document.getElementById('searchKeyword').value;
    const url = `/student/search?search=${encodeURIComponent(searchKeyword)}`;
    // Sửa đổi URL bằng phương thức replaceState
    history.replaceState(null, "", url);
    if (searchBy === 'document') {
        $.ajax({
            url: "/api/student/search_document", // URL endpoint của API tìm kiếm
            method: "GET",
            data: {
                search: searchKeyword,
                skip: 0
            },
            success: function (data) {
                overlay.style.display = 'none';
                let results = $('#results ul');
                console.log(results);
                results.empty();
                if (data.length == 0) {
                    let li = $('<li class="d-flex justify-content-center"  style="margin: 2rem 2rem">');
                    let img = $('<img src="/images/no-result.gif" style="width: 50%">').attr('alt', 'No result');
                    li.append(img);
                    results.append(li);
                } else {
                    $.each(data, function (index, item) {

                        let li = $('<li class="border-bottom" style="margin: 2rem 2rem">');

                        let div1 = $('<div>').addClass('d-flex document-view-info');
                        let div2 = $('<div>').addClass('doc-info-head grid__column-9');
                        let p1 = $('<p>').addClass('doc-info-title');
                        let a = $('<a>').attr('href', '/student/documents/' + item.documentId);
                        let span1 = $('<span>').addClass('highlighted-title').text(item.title);
                        a.append(span1);
                        p1.append(a);
                        div2.append(p1);

                        let p2 = $('<p>').addClass('doc-info-description');
                        let span2 = $('<span>').addClass('highlighted-description').text(item.description);
                        p2.append(span2);
                        div2.append(p2);
                        div1.append(div2);

                        let div3 = $('<div>').addClass('doc-info grid__column-3 d-flex justify-content-center');
                        let p3 = $('<p>');
                        let span3 = $('<span>').addClass('doc-info-date').text(item.lastModifiedDate);
                        p3.append(span3);
                        div3.append(p3);
                        div1.append(div3);

                        li.append(div1);

                        let div4 = $('<div>').addClass('doc-info');
                        let p4 = $('<p>');
                        let span4 = $('<span>').addClass('doc-info-description highlighted-content').text(item.content);
                        p4.append(span4);
                        div4.append(p4);

                        li.append(div4);

                        results.append(li);
                        highlightSearchResults(); // Thực hiện highlight sau khi thêm dữ liệu
                    });
                }
            },
            error: function (error) {
                // Xử lý lỗi trong quá trình gửi yêu cầu
            }
        });
    } else {
        $.ajax({
            url: '/api/student/search_course',
            type: 'GET',
            data: {
                skip: 0,
                search: searchKeyword
            },
            success: function (data) {
                overlay.style.display = 'none';
                let results = $('#results ul');
                console.log(data);
                results.empty();
                if (data.length == 0) {
                    let li = $('<li class="d-flex justify-content-center"  style="margin: 2rem 2rem">');
                    let img = $('<img src="/images/no-result.gif" style="width: 50%">').attr('alt', 'No result');
                    li.append(img);
                    results.append(li);
                } else {
                    $.each(data, function (index, item) {
                        console.log(item)
                        let li = $('<li class="border-bottom" style="margin: 2rem 2rem">');

                        let div1 = $('<div>').addClass('d-flex document-view-info');
                        let div2 = $('<div>').addClass('doc-info-head grid__column-9');
                        let p1 = $('<p>').addClass('doc-info-title');
                        let a = $('<a>').attr('href', '/student/courses/' + item.courseId);
                        let span1 = $('<span>').addClass('highlighted-title').text(item.code);
                        a.append(span1);
                        p1.append(a);
                        div2.append(p1);

                        let p2 = $('<p>').addClass('doc-info-description');
                        let span2 = $('<span>').addClass('highlighted-description').text(item.name);
                        p2.append(span2);
                        div2.append(p2);
                        div1.append(div2);

                        let div3 = $('<div>').addClass('doc-info grid__column-3 d-flex justify-content-center');
                        let p3 = $('<p>');
                        let span3 = $('<span>').addClass('doc-info-date').text(item.lastModifiedDate);
                        p3.append(span3);
                        div3.append(p3);
                        div1.append(div3);

                        li.append(div1);

                        let div4 = $('<div>').addClass('doc-info');
                        let p4 = $('<p>');
                        let span4 = $('<span>').addClass('doc-info-description highlighted-content').text(item.description);
                        p4.append(span4);
                        div4.append(p4);

                        li.append(div4);

                        results.append(li);
                    });
                    highlightSearchResults(); // Thực hiện highlight sau khi thêm dữ liệu
                }
            },
            error: function () {
                console.log("false")
            }
        });
    }
}

function loadCourses() {
    overlay.style.display = 'block';
    firstSearch.style.display = 'none';
    searchBy = 'course';
    let searchKeyword = document.getElementById('searchKeyword').value;
    $.ajax({
        url: '/api/student/search_course',
        type: 'GET',
        data: {
            skip: 0,
            search: searchKeyword
        },

        success: function (data) {
            overlay.style.display = 'none';
            let results = $('#results ul');

            if (data.length == 0) {
                results.empty();
                let li = $('<li class="d-flex justify-content-center"  style="margin: 2rem 2rem">');
                let img = $('<img src="/images/no-result.gif" style="width: 50%">').attr('alt', 'No result');
                li.append(img);
                results.append(li);
            } else {
                results.empty();
                $.each(data, function (index, item) {
                    let li = $('<li class="border-bottom" style="margin: 2rem 2rem">');

                    let div1 = $('<div>').addClass('d-flex document-view-info');
                    let div2 = $('<div>').addClass('doc-info-head grid__column-9');
                    let p1 = $('<p>').addClass('doc-info-title');
                    let a = $('<a>').attr('href', '/student/courses/' + item.courseId);
                    let span1 = $('<span>').addClass('highlighted-title').text(item.code);
                    a.append(span1);
                    p1.append(a);
                    div2.append(p1);

                    let p2 = $('<p>').addClass('doc-info-description');
                    let span2 = $('<span>').addClass('highlighted-description').text(item.name);
                    p2.append(span2);
                    div2.append(p2);
                    div1.append(div2);

                    let div3 = $('<div>').addClass('doc-info grid__column-3 d-flex justify-content-center');
                    let p3 = $('<p>');
                    let span3 = $('<span>').addClass('doc-info-date').text(item.lastModifiedDate);
                    p3.append(span3);
                    div3.append(p3);
                    div1.append(div3);

                    li.append(div1);

                    let div4 = $('<div>').addClass('doc-info');
                    let p4 = $('<p>');
                    let span4 = $('<span>').addClass('doc-info-description highlighted-content').text(item.description);
                    p4.append(span4);
                    div4.append(p4);

                    li.append(div4);

                    results.append(li);
                });
                highlightSearchResults(); // Thực hiện highlight sau khi thêm dữ liệu
            }
        },
        error: function () {
            console.log("false")
        }
    });
}

function loadDocuments() {
    overlay.style.display = 'block';
    firstSearch.style.display = 'none';
    searchBy = 'document';
    let searchKeyword = document.getElementById('searchKeyword').value;

    $.ajax({
        url: '/api/student/search_document',
        type: 'GET',
        data: {
            skip: 0,
            search: searchKeyword
        },
        success: function (data) {
            let results = $('#results ul');
            overlay.style.display = 'none';
            if (data.length == 0) {
                results.empty();
                let li = $('<li class="d-flex justify-content-center"  style="margin: 2rem 2rem">');
                let img = $('<img src="/images/no-result.gif" style="width: 50%">').attr('alt', 'No result');
                li.append(img);
                results.append(li);
            } else {
                results.empty();
                $.each(data, function (index, item) {
                    console.log(item)
                    let li = $('<li class="border-bottom" style="margin: 2rem 2rem">');

                    let div1 = $('<div>').addClass('d-flex document-view-info');
                    let div2 = $('<div>').addClass('doc-info-head grid__column-9');
                    let p1 = $('<p>').addClass('doc-info-title');
                    let a = $('<a>').attr('href', '/student/documents/' + item.documentId);
                    let span1 = $('<span>').addClass('highlighted-title').text(item.title);
                    a.append(span1);
                    p1.append(a);
                    div2.append(p1);

                    let p2 = $('<p>').addClass('doc-info-description');
                    let span2 = $('<span>').addClass('highlighted-description').text(item.description);
                    p2.append(span2);
                    div2.append(p2);
                    div1.append(div2);

                    let div3 = $('<div>').addClass('doc-info grid__column-3 d-flex justify-content-center');
                    let p3 = $('<p>');
                    let span3 = $('<span>').addClass('doc-info-date').text(item.lastModifiedDate);
                    p3.append(span3);
                    div3.append(p3);
                    div1.append(div3);

                    li.append(div1);

                    let div4 = $('<div>').addClass('doc-info');
                    let p4 = $('<p>');
                    let span4 = $('<span>').addClass('doc-info-description highlighted-content').text(item.content);
                    p4.append(span4);
                    div4.append(p4);

                    li.append(div4);

                    results.append(li);
                });
                highlightSearchResults(); // Thực hiện highlight sau khi thêm dữ liệu
            }
        },
        error: function () {
            console.log("false")
        }
    });
}

function appendDataToResults(data) {
    var results = $('#results ul');
    $.each(data, function (index, document) {
        var li = $('<li class="border-bottom" style="margin: 2rem 2rem">');

        var div1 = $('<div>').addClass('d-flex document-view-info');
        var div2 = $('<div>').addClass('doc-info-head grid__column-9');
        var p1 = $('<p>').addClass('doc-info-title');
        var a = $('<a>').attr('href', '/student/documents/' + document.id);
        var span1 = $('<span>').addClass('highlighted-title').text(document.title);
        a.append(span1);
        p1.append(a);
        div2.append(p1);

        var p2 = $('<p>').addClass('doc-info-description');
        var span2 = $('<span>').addClass('highlighted-description').text(document.description);
        p2.append(span2);
        div2.append(p2);
        div1.append(div2);

        var div3 = $('<div>').addClass('doc-info grid__column-3 d-flex justify-content-center');
        var p3 = $('<p>');
        var span3 = $('<span>').addClass('doc-info-date').text(document.lastModifiedDate);
        p3.append(span3);
        div3.append(p3);
        div1.append(div3);

        li.append(div1);

        var div4 = $('<div>').addClass('doc-info');
        var p4 = $('<p>');
        var span4 = $('<span>').addClass('doc-info-description highlighted-content').text(document.content);
        p4.append(span4);
        div4.append(p4);

        li.append(div4);

        results.append(li);
    });
}

$(document).ready(function () {
    highlightSearchResults();
});

function highlightSearchResults() {
    let searchKeyword = document.getElementById('searchKeyword').value.trim();
    var titleElements = document.getElementsByClassName("highlighted-title");
    var descriptionElements = document.getElementsByClassName("highlighted-description");
    var contentElements = document.getElementsByClassName("highlighted-content");

    for (var i = 0; i < titleElements.length; i++) {
        var titleElement = titleElements[i];
        var highlightedTitle = titleElement.textContent.replace(new RegExp(searchKeyword, "gi"), "<span class='highlight'>$&</span>");
        titleElement.innerHTML = highlightedTitle;
    }

    for (var i = 0; i < descriptionElements.length; i++) {
        var descriptionElement = descriptionElements[i];
        var lines = descriptionElement.textContent.split("\n");
        var highlightedLines = [];

        for (var j = 0; j < lines.length; j++) {
            var line = lines[j];
            if (line.toLowerCase().includes(searchKeyword.toLowerCase())) {
                var highlightedLine = line.replace(new RegExp(searchKeyword, "gi"), "<span class='highlight'>$&</span>");
                highlightedLines.push(highlightedLine);

                if (highlightedLines.length >= 2) {
                    break;
                }
            }
        }

        var highlightedText = highlightedLines.join("\n");
        descriptionElement.innerHTML = highlightedText;
    }

    for (var i = 0; i < contentElements.length; i++) {
        var contentElement = contentElements[i];
        var lines = contentElement.textContent.split("\n");
        var highlightedLines = [];

        for (var j = 0; j < lines.length; j++) {
            var line = lines[j];
            if (line.toLowerCase().includes(searchKeyword.toLowerCase())) {
                var highlightedLine = line.replace(new RegExp(searchKeyword, "gi"), "<span class='highlight'>$&</span>");
                highlightedLines.push(highlightedLine);

                if (highlightedLines.length >= 2) {
                    break;
                }
            }
        }

        var highlightedText = highlightedLines.join("\n");
        contentElement.innerHTML = highlightedText;
    }

    // thêm

    var keywords = searchKeyword.split(' ');

    // Tạo RegExp để highlight từng từ
    var regexString = keywords.map(function (keyword) {
        return keyword.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'); // Escape special characters
    }).join('|');

    var regex = new RegExp(regexString, "gi");

    for (var i = 0; i < titleElements.length; i++) {
        var titleElement = titleElements[i];
        var highlightedTitle = titleElement.textContent.replace(regex, "<span class='highlight'>$&</span>");
        titleElement.innerHTML = highlightedTitle;
    }

    // Đối với descriptionElements
    for (var i = 0; i < descriptionElements.length; i++) {
        var descriptionElement = descriptionElements[i];
        var lines = descriptionElement.textContent.split("\n");
        var highlightedLines = lines.map(function (line) {
            return line.replace(regex, "<span class='highlight'>$&</span>");
        });

        var highlightedText = highlightedLines.join("\n");
        descriptionElement.innerHTML = highlightedText;
    }

// Đối với contentElements
    for (var i = 0; i < contentElements.length; i++) {
        var contentElement = contentElements[i];
        var lines = contentElement.textContent.split("\n");
        var highlightedLines = lines.map(function (line) {
            return line.replace(regex, "<span class='highlight'>$&</span>");
        });

        var highlightedText = highlightedLines.join("\n");
        contentElement.innerHTML = highlightedText;
    }

}