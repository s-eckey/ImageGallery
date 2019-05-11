module.exports = {
	outputDir: '../backend/src/main/resources/static',
	devServer: {
		proxy: 'http://localhost:8080'
	}
};
