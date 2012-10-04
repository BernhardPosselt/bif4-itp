(function() {
  var __indexOf = [].indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; };

  angular.module('WebChat').filter('groupInChannel', function() {
    return function(groups, args) {
      var group, result, _i, _len, _ref;
      result = [];
      for (_i = 0, _len = groups.length; _i < _len; _i++) {
        group = groups[_i];
        if (_ref = group.id, __indexOf.call(args.groups, _ref) >= 0) {
          result.push(group);
        }
      }
      return result;
    };
  });

}).call(this);
