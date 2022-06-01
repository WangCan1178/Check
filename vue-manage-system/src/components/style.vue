<script>
    /*
 * @Author: chengsl
 * @Date: 2021-12-08 09:48:07
 * @LastEditors: chengsl
 * @LastEditTime: 2021-12-09 15:52:45
 * @Description: file content
 */

    /**
     * 获取一系列 主题色
     * 入参：67c23a
     * 结果：['67c23a', '103,194,58', '#76c84e', '#85ce61', '#95d475', '#a4da89', '#b3e19d', '#c2e7b0', '#d1edc4', '#e1f3d8', '#f0f9eb', '#5daf34']
     */
    function getThemeCluster(theme, type = '') {
        const tintColor = (color, tint) => {
            let red = parseInt(color.slice(0, 2), 16)
            let green = parseInt(color.slice(2, 4), 16)
            let blue = parseInt(color.slice(4, 6), 16)

            if (tint === 0) {
                // when primary color is in its rgb space
                return [red, green, blue].join(',')
            } else {
                red += Math.round(tint * (255 - red))
                green += Math.round(tint * (255 - green))
                blue += Math.round(tint * (255 - blue))

                red = red.toString(16)
                green = green.toString(16)
                blue = blue.toString(16)

                return `#${red}${green}${blue}`
            }
        }

        const shadeColor = (color, shade) => {
            let red = parseInt(color.slice(0, 2), 16)
            let green = parseInt(color.slice(2, 4), 16)
            let blue = parseInt(color.slice(4, 6), 16)

            red = Math.round((1 - shade) * red)
            green = Math.round((1 - shade) * green)
            blue = Math.round((1 - shade) * blue)

            red = red.toString(16)
            green = green.toString(16)
            blue = blue.toString(16)

            return `#${red}${green}${blue}`
        }

        const clusters = [theme]
        for (let i = 0; i <= 9; i++) {
            clusters.push(tintColor(theme, Number((i / 10).toFixed(2))))
        }
        clusters.push(shadeColor(theme, 0.1))
        // 这行代码是我自己后面的其他优化所需，可删
        if (type && type === 'new' && clusters.length) {
            clusters.forEach((theme, index) => {
                if (index > 1) {
                    document.body.style.setProperty(`--theme-weaken-color-${index}`, clusters[index]) // 逐渐淡化颜色
                }
            })
            console.log('主题色系-------------------', clusters)
        }
        return clusters
    }

    /**
     * @param style 老的css样式代码
     * @param oldCluster 老的一些列主题色  待替换
     * @param newCluster 新的一系列主题色  替换成
     *
     * @returns newStyle 新的 css样式代码 替换后的
     */
    function updateStyle(style, oldCluster, newCluster) {
        let newStyle = style
        oldCluster.forEach((color, index) => {
            // 将老颜色替换成新颜色
            newStyle = newStyle.replace(new RegExp(color, 'ig'), newCluster[index].trim()) // 全局替换 不区分大小写 去掉多余空格
        })
        return newStyle
    }

    /**
     * main
     */
    import ElementUiCSS from './ElementUiCSS.js' // 就是上文说的默认样式 index.css; (字符串)
    export const updateThemeColor = function(val) {
        if (typeof val !== 'string' || val.length === 0) return
        const ORIGINAL_THEME = '#409EFF' // default color (element ui的默认主题色，所有我们根据这个去改)
        const ThemeCode = ElementUiCSS
            .replace(/@font-face{[^}]+}/g, '') // 去掉字体样式
            .replace(/.el-icon-[a-zA-Z0-9-:^]+before{content:"[^}]+}/g, '') // 去掉图标样式
        // require('element-ui/lib/theme-chalk/index.css')

        console.log('updateThemeColor ------------------------ in')
        console.log('ORIGINAL_THEME', ORIGINAL_THEME)
        // try {
        //   console.log('ThemeCode', ThemeCode)
        //   console.log('ThemeCode', String(ThemeCode))
        //   console.log('ThemeCode', ThemeCode.innerText)
        // } catch (error) {
        //   console.log(error)
        // }
        // 得到一系列 主题色颜色 (我们需要的颜色 '产出')
        const themeCluster = getThemeCluster(val.replace('#', ''), 'new')
        /**
         * 入参：'chalk'(旧css代码), 'chalk-style'(style的id)
         * 直接 将老的 css 代码里 待改的旧颜色改成 新颜色 然后将新的样式 插入到head标签里
         */
        const getHandler = id => {
            return () => {
                // 得到一系列 主题色颜色 (原始的一些列颜色  待改)
                const originalCluster = getThemeCluster(ORIGINAL_THEME.replace('#', ''))
                const newStyle = updateStyle(ThemeCode, originalCluster, themeCluster)
                // console.log('newStyle', newStyle)

                let styleTag = document.getElementById(id)
                if (!styleTag) {
                    styleTag = document.createElement('style')
                    styleTag.setAttribute('id', id)
                    document.head.appendChild(styleTag)
                }
                styleTag.innerText = newStyle
            }
        }
        const chalkHandler = getHandler('chalk-style')
        chalkHandler()

        console.log('updateThemeColor ------------------------ end')
    }


</script>