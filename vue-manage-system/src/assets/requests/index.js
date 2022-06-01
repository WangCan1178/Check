import axios from "axios"
import _ from "lodash"
import qs from "qs"

// 处理请求参数
const coverFormData = (obj) => {
    let data = Object.keys(obj.data).map(item => {
        let value = obj.data[item];
        if(_.isArray(value) || _.isObject(value)){
            value = JSON.stringify(value)
        }
        return encodeURIComponent(item) + '=' + encodeURIComponent(value);
    }).join('&');

    return {data: data, url: obj.url};
}

function post(obj) {
    const { data, url } = coverFormData(obj);
    return new Promise((resolve, reject) => {
        axios.post(url,  data)
            .then(res => {
                // obj.success ? obj.success(res) : null
                resolve(res.data);
            })
            .catch(error => {
                // obj.error ? obj.error(error) : null;
                reject(error);
            })
    })

}

function get(obj) {
    const { data, url } = coverFormData(obj);
    return new Promise((resolve, reject) => {
        axios.get(url,  data)
            .then(res => {
                // obj.success ? obj.success(res) : null
                resolve(res.data);
            })
            .catch(error => {
                // obj.error ? obj.error(error) : null;
                reject(error);
            })
    })

}

let requests = {}
requests.post = post
requests.get = get

export default requests