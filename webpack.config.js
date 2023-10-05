const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const { PurgeCSSPlugin } = require('purgecss-webpack-plugin');
var path = require('path');
const glob = require('glob-all');

module.exports = {
    entry: {
        HelloWorldWidget: './src/main/js/HelloWorldWidget.js',
        TodoAppWidget: './src/main/js//todo-app/TodoAppWidget.js',
        BootstrapCSS: './src/main/scss/bootstrap/bootstrap.scss',
        MainCSS: './src/main/scss/main.scss',
    },
    devtool: 'source-map',
    cache: true,
    mode: 'production',
    output: {
        library: '[name]',
        libraryTarget: 'umd',
        path: path.join(__dirname, './target/classes/static/'),
        filename: 'dist/js/[name].js'
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
            {
                test: /\.s[ac]ss$/i,
                use: [
                    MiniCssExtractPlugin.loader,
                    "css-loader",
                    "sass-loader"
                ]
            },
            {
                test: /\.(png|jpg|ico)$/,
                type: 'asset/resource',
                generator: {
                    filename: 'images/[name][ext]'
                }
            },
            {
                test: /\.(svg)$/,
                include: [
                    path.resolve(__dirname, "src/main/resources/static/images")
                ],
                type: 'asset/resource',
                generator: {
                    filename: 'images/[name][ext]'
                }
            },
            {
                test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
                exclude: [
                    path.resolve(__dirname, "src/main/resources/static/images")
                ],
                type: 'asset/resource',
                generator: {
                    filename: 'fonts/[name][ext]'
                }
            },
            {
                test: /\.json$/i,
                use: ["json-loader"],
                type: 'javascript/auto'
            }
        ]
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: ({chunk: {name}}) => 'dist/css/' + name.replace(/CSS$/, '').toLowerCase()+ '.min.css'
        }),
//        new PurgeCSSPlugin({
//            paths: () => glob.sync(['./src/main/resources/templates/**/*.html', './src/main/js/**/*.js']),
//            safelist: []
//        })
    ]
};
