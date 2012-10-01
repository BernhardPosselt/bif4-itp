angular.module('WebChat').filter 'userInGroup', ->
    return (users, args) ->
        result = []
        for user in users
            for groupId in user.groups
                if args.groupid == groupId
                    result.push(user)
        return result