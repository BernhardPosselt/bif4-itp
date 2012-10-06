angular.module('WebChat').filter 'userInGroup', ->
    return (users, groupId) ->
        result = []
        for user in users
            for userGroupId in user.groups
                if userGroupId == groupId
                    result.push(user)
        return result