(function() {
  var webchat;

  webchat = require('webchat');

  webchat.Model = (function() {

    function _Class(type) {
      this.type = type;
      this.items = [];
    }

    _Class.prototype.handle = function(message) {
      switch (message.action) {
        case 'update':
          return this.update(message.data);
        case 'create':
          return this.create(message.data);
        case 'delete':
          return this["delete"](message.data);
      }
    };

    _Class.prototype.create = function(item) {
      return this.items.push(item);
    };

    _Class.prototype.update = function(updatedItem) {
      var counter, item, _i, _len, _ref, _results;
      _ref = this.items;
      _results = [];
      for (counter = _i = 0, _len = _ref.length; _i < _len; counter = ++_i) {
        item = _ref[counter];
        if (item.id === updatedItem.id) {
          _results.push(this.items[counter] = updatedItem);
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    };

    _Class.prototype["delete"] = function(removedItem) {
      var counter, item, removeItemId, _i, _len, _ref;
      removeItemId = -1;
      _ref = this.items;
      for (counter = _i = 0, _len = _ref.length; _i < _len; counter = ++_i) {
        item = _ref[counter];
        if (item.id === removedItem.id) {
          removeItemId = counter;
        }
      }
      if (removeItemId >= 0) {
        return this.items.splice(removeItemId, 1);
      }
    };

    _Class.prototype.getItemById = function(id) {
      var item, _i, _len, _ref;
      _ref = this.items;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        item = _ref[_i];
        if (item.id === id) {
          return item;
        }
      }
    };

    _Class.prototype.canHandle = function(messageTypes) {
      if (type === this.type) {
        return true;
      } else {
        return false;
      }
    };

    return _Class;

  })();

}).call(this);
