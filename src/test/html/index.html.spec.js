var expect = require('chai').expect;
var cheerio = require('cheerio');

describe('index.html', function() {

    var page;

    beforeEach(function() {
        var pageContent = require('fs').readFileSync('./src/main/resources/search.html').toString();
        page = cheerio.load(pageContent);
    });

    it('contains the input box', function() {
        expect(page('input#criteria').length).to.equal(1);
    });

    it('contains the invitation label', function() {
        expect(page('label#invitation').length).to.equal(1);
    });

    it('contains the search trigger', function() {
        expect(page('button#go').length).to.equal(1);
    });

    it('targets the expected endpoint', function() {
        expect(page('#go').attr('onclick')).to.equal("new Fetcher($).executeSearch();");
    });
});