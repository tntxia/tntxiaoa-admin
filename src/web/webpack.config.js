module.exports = {
    entry: "./index.js",
    output: {
        path: __dirname,
        filename: "../../WebContent/js/bundle.js"
    },
    module: {
        loaders: [
            { test: /\.css$/, loader: "style-loader!css-loader" }
        ]
    }
};