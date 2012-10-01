angular.module('WebChat').filter 'userInChannel', ->
    return (users, args) ->
        result = []
        for user in users
            for channelUserId in args.users
                if user.id == channelUserId
                    result.push(user)
        return result