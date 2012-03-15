
/*
This class represents one channel
*/

(function() {
  var Channel;

  Channel = (function() {

    Channel.updates = 0;

    Channel.id = -1;

    Channel.channels = $('#channels ul');

    function Channel(name) {
      this.name = name;
    }

    Channel.prototype.insert = function(position) {
      this.dom_elem = $('<li>');
      this.channels.add(this.dom_elem);
      return $.data(this.dom_elem, 'id', this.id);
    };

    return Channel;

  })();

}).call(this);
