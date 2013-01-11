angular.module('WebChat').filter 'userInChannel', ->
    return (users, channel) ->
        result = []
        if channel == undefined or channel == null or channel.users == undefined
            return result
        for user in users
            if user.id in channel.users
                result.push(user)
        return result