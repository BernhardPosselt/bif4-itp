/*
This class represents one channel item
*/
var Channel;

Channel = (function() {

  function Channel() {
    this.channels = $("#channels ul");
    this.id = 0;
    this.caption = "";
    this.unread_entries = 0;
    this.sticky = false;
  }

  Channel.prototype.instance = function(id) {
    this.id = id;
    return this.dom_elem = this.channels.children("li");
  };

  Channel.prototype.create = function() {
    this.dom_elem = $("<li>");
    this.dom_elem.append($("<a href=''>" + this.caption + "</a>"));
    return this.channels.append(this.dom_elem);
  };

  return Channel;

})();
