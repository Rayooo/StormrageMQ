
function getHost() {
    return "http://localhost:4101";
}

function post(api, param, callback, errCallback) {
    $.post(getHost() + api , param ,callback).fail(errCallback);
}

function getSessionStorage(name) {
    return JSON.parse(sessionStorage.getItem(name));
}

function setSessionStorage(name,object) {
    sessionStorage.setItem(name, JSON.stringify(object));
}

function getUserInfoFromSessionStorage() {
    return getSessionStorage("user")
}

