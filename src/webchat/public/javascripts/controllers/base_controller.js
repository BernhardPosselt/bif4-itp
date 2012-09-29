// Generated by CoffeeScript 1.3.3
(function() {

  window.WebChat || (window.WebChat = {});

  window.WebChat.BaseController = (function() {

    function _Class($scope, type) {
      this.$scope = $scope;
      this.type = type;
    }

    _Class.prototype.create = function(item) {
      return this.$scope.items.push(item);
    };

    _Class.prototype.update = function(updatedItem) {
      var counter, item, _i, _len, _ref, _results;
      _ref = this.$scope.items;
      _results = [];
      for (counter = _i = 0, _len = _ref.length; _i < _len; counter = ++_i) {
        item = _ref[counter];
        if (item.id === updatedItem.id) {
          _results.push(this.$scope.items[counter] = updatedItem);
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    };

    _Class.prototype["delete"] = function(removedItem) {
      var counter, item, removeItemId, _i, _len, _ref;
      removeItemId = -1;
      _ref = this.$scope.items;
      for (counter = _i = 0, _len = _ref.length; _i < _len; counter = ++_i) {
        item = _ref[counter];
        if (item.id === removedItem.id) {
          removeItemId = counter;
        }
      }
      if (removeItemId >= 0) {
        return this.$scope.items.splice(removeItemId, 1);
      }
    };

    _Class.prototype.canHandle = function(messageType) {
      return messageType === this.type;
    };

    return _Class;

  })();

}).call(this);
