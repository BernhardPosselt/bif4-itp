/*
Manages all channel objects in the channellist
*/
var ChannelList;

ChannelList = (function() {

  function ChannelList() {
    this.dom_elem = $("#channels ul");
    this.list = [];
  }

  ChannelList.prototype.update = function(list) {};

  ChannelList.prototype.insert = function(elem, position) {
    return this.list[positions] = elem;
  };

  return ChannelList;

})();
