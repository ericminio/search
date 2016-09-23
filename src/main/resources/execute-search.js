var selfFetcher;

Fetcher = function($) {
    selfFetcher = this;
    this.page = $;
}

Fetcher.prototype.executeSearch = function() {
    var products = '/products?criteria=' + this.page('#criteria').val();
    this.page.get(products, this.displayResults);
};

Fetcher.prototype.displayResults = function(data) {
    spots = selfFetcher.page('.spot');
    locations = data.split(" ");
    for (var index=0; index<locations.length; index++) {
        var location = locations[index];
        var locationData = location.split(":");
        var count = locationData[1];
        var spot = selfFetcher.page('#spot-' + (index+1));
        spot.text(locationData[1]);
        spot.attr('class', 'spot');
        spot.attr('title', 'location: ' + locationData[0]);

        if (count == 0) {
            spot.addClass('empty');
        }
    }
};