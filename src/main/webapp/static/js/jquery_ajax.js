// 此文件中的内容需要 jQuery 和 layer 的支持

/**
 * post 请求
 * @param url 请求路径
 * @param data 发送的数据
 * @param successCallback 请求发送成功后执行的方法, 成功指请求本身, 与业务的结果无关
 *  只支持 function 类型, 业务的返回数据会作为参数传入
 */
function ajaxRequest_post(url, data, successCallback) {
    ajaxRequest_all(url, null, null, null,
        data, null, null, null, successCallback
    );
}

/**
 * GET 请求
 *
 * @param url 请求路径
 * @param data 发送的数据
 * @param successCallback 请求发送成功后执行的方法, 成功指请求本身, 与业务的结果无关
 *  只支持 function 类型, 业务的返回数据会作为参数传入
 */
function ajaxRequest_get(url, data, successCallback) {
    ajaxRequest_all(url, null, "GET", null,
        data, null, null, null, successCallback
    );
}

/**
 * Tips Ajax 请求
 *
 * @param url 请求地址
 * @param async 是否异步, 除明确传入 false 外都视为 true, true 代表异步
 * @param type 请求类型, 默认为 POST
 * @param contentType 发送的数据类型, 默认为: application/x-www-form-urlencoded
 * @param data 发送的数据
 * @param dataType 接收的数据类型, 默认为 json
 * @param beforeSend 发送前执行的方法, 只支持 function 类型
 *  会获得 XMLHttpRequest 对象作为参数, 如果返回 false 则取消本次请求
 * @param complete 请求完成后执行的方法, 只支持 function 类型
 *  有两个参数, 一个是 XNLHttpRequest 和请求类型
 * @param successCallback 请求发送成功后执行的方法, 成功指请求本身, 与业务的结果无关
 *  只支持 function 类型, 业务的返回数据会作为参数传入
 * @param errorCallback 请求发送失败后执行的方法, 失败指请求本身, 与业务的结果无关
 *  只支持 function 类型, 三个参数: XMLHttpRequest、错误信息、捕获的异常对象
 * @param showMask 是否开启蒙版, 除明确传入 false 外, 都是视为开启
 */
function ajaxRequest_all(url, async, type, contentType, data, dataType,
                         beforeSend, complete, successCallback, errorCallback, showMask) {

    // 开启蒙版
    let index;
    if (showMask !== false) {
        index = layer.load(1);
    }

    // 数据空值处理
    data = data || {};

    // 参数类型空值处理
    contentType = contentType || "application/x-www-form-urlencoded;charset=UTF-8"; // 发送的数据类型

    // Tips jQuery 在 3.5.0 以后, url 参数已经挪到 options 外面了
    $.ajax(wabApp + url, {
        async: async !== false, // 是否异步
        type: type || "POST", // 请求类型
        data: contentType.search("json") === -1 ? data : JSON.stringify(data), // 参数
        contentType: contentType,
        dataType: dataType || "json", // 接受的返回值类型
        beforeSend: function (XHR) {
            // 只有当 successCallback 存在且为 function 类型时才执行
            let execute = true;
            if (beforeSend && typeof beforeSend == "function") {
                execute = beforeSend(XHR);
            }
            if (execute === false) {
                if (index) {
                    layer.close(index);
                }
                return execute;
            }
        },
        success: function (result) {
            // 只有当 successCallback 存在且为 function 类型时才执行
            if (successCallback && typeof successCallback == "function") {
                successCallback(result);
            } else {
                ajaxRequestDefaultSuccessCallback(result);
            }
        },
        error: function (XHR, message, exception) {
            // 只有当 errorCallback 存在且为 function 类型时才执行
            if (errorCallback && typeof errorCallback == "function") {
                errorCallback(XHR, message, exception);
            } else {
                ajaxRequestDefaultErrorCallback(XHR, message, exception);
            }
        },
        complete: function (XHR, ts) {
            // 关闭蒙版
            if (index) {
                layer.close(index);
            }
            // 只有当 complete 存在且为 function 类型时才执行
            if (complete && typeof complete == "function") {
                complete(XHR, ts);
            }
        }
    })
}

/**
 * ajaxRequest 的默认成功回调
 * @param result
 */
function ajaxRequestDefaultSuccessCallback(result) {
    layer.alert("操作成功!");
}

/**
 * ajaxRequest 的默认错误回调
 *
 * @param xhr XMLHttpRequest
 * @param message 可能为: null、timeout、error、notmodified、parsererror TODO 这都是什么?
 * @param exception 捕获的异常对象
 */
function ajaxRequestDefaultErrorCallback(xhr, message, exception) {
    console.log(xhr);
    console.log(exception);
    layer.alert(message || "请求因未知原因失败!");
}