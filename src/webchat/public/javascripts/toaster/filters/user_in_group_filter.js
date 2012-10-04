(function() {

  angular.module('WebChat').filter('userInGroup', function() {
    return function(users, args) {
      var groupId, result, user, _i, _j, _len, _len1, _ref;
      result = [];
      for (_i = 0, _len = users.length; _i < _len; _i++) {
        user = users[_i];
        _ref = user.groups;
        for (_j = 0, _len1 = _ref.length; _j < _len1; _j++) {
          groupId = _ref[_j];
          if (args.groupid === groupId) {
            result.push(user);
          }
        }
      }
      return result;
    };
  });

}).call(this);
