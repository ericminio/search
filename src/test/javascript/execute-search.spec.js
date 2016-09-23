var expect = require('chai').expect;
var sinon = require('sinon');
require('chai').use(require('sinon-chai'));

require('../../main/resources/execute-search.js');

describe('search', function() {

    var $;
    var fetcher;

    beforeEach(function() {
        $ = require('jquery')(require("jsdom").jsdom().defaultView);
        fetcher = new Fetcher($);
        $('head').append('<style type="text/css"> .spot{ display:inline-block; background-color: #008B8B; } </style>');
        $('head').append('<style type="text/css"> .hidden{ display:none } </style>');
        $('head').append('<style type="text/css"> .empty{ background-color: red; } </style>');
        $('body').append('<input id="criteria" />');
        $('body').append('<label id="spot-1" class="spot hidden"></label>');
        $('body').append('<label id="spot-2" class="spot hidden"></label>');
    });

    afterEach(function() {
        $('#criteria').remove();
    });

    it('sends the products request', function() {
        $.get = sinon.spy();
        $('#criteria').val('any');
        fetcher.executeSearch();

        expect($.get).to.have.been.calledWith('/products?criteria=any', fetcher.displayResults);
    });

    it('inject the result in the view', function() {
        fetcher.displayResults("A-1-1:42 B-2-2:21");

        expect($('#spot-1').text()).to.equal("42");
        expect($('#spot-2').text()).to.equal("21");
    });

    it('show the results', function() {
        fetcher.displayResults("A-1-1:42 B-2-2:21");

        expect($('#spot-1').css('display')).to.equal("inline-block");
        expect($('#spot-2').css('display')).to.equal("inline-block");
    });

    it('reds empty spots', function() {
        fetcher.displayResults("A-1-1:42 B-2-2:0");

        expect($('#spot-2').css('background-color')).to.equal('red');
    });

    it('tooltips the location', function() {
        fetcher.displayResults("A-1-1:42 B-2-2:0");

        expect($('#spot-2').attr('title')).to.equal('location: B-2-2');
    });

});