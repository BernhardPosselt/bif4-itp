#<< app

angular.module('WebChat').filter 'userInChannel', ->
    return (users, args) ->
        result = []
        for user in users
            if user.id in args.users
                result.push(user)
        return result