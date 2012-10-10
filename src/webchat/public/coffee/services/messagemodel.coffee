angular.module('WebChat').factory '_MessageModel', 
    ['_Model', 'Smileys', 'UserModel', 'ActiveUser',
     (_Model, Smileys, UserModel, ActiveUser) ->

        class MessageModel extends _Model

            constructor: () ->
                super('message')

            create: (item) ->
                item = @decorate(item)
                super(item)


            update: (item) ->
                item = @decorate(item)
                super(item)


            decorate: (item) ->

                # only enhance text messages
                if item.type == 'text'
                
                    # check if we were hightlighted
                    user = UserModel.getItemById(ActiveUser.id)
                    highlightName = user.firstname + user.lastname
                    if item.message.indexOf(highlightName) != -1
                        item.hightlighted = true
                        document.getElementById('sounds').play()
                    else
                        item.hightlighted = false

                    item.message = @cleanXSS(item.message)
                    item.message = @sugarText(item.message)
                    item.message = @convertNewLines(item.message)

                return item


            cleanXSS: (text) ->
                return $('<div>').text(text).html();


            # wraps links in <a> tags, pictures in <img> tags
            # msg: the message we search
            # return: the final message
            sugarText: (msg) ->
                # first place all urls in <a> tags
                msg = @createLinks(msg)
                
                # add smileys
                smileys = Smileys
                for key, smile of smileys.getSmileys()
                    img = '<img width="50" height="50" alt="' + key + '" src="' + smileys.getSmiley(key) + '" />'
                    middle_line_regex = new RegExp("(" + @escapeForRegex(key) + ")([\.\?!,;]*) ", "g")
                    msg = msg.replace(middle_line_regex, " " + img + "$2 ")
                    break_line_regex = new RegExp(@escapeForRegex(key) + "([\.\?!,;]*)<br", "g")
                    msg = msg.replace(break_line_regex, img + '$1<br')
                    new_line_regex = new RegExp(@escapeForRegex(key) + "([\.\?!,;]*)\\n", "g")
                    msg = msg.replace(new_line_regex, img + '$1\n')
                    end_line_regex = new RegExp( "(.*)" + @escapeForRegex(key) + "([\.\?!,;]*)$", "g")
                    msg = msg.replace(end_line_regex, "$1" + img + "$2")
                    start_line_regex = new RegExp( "^" + @escapeForRegex(key) + "([\.\?!,;]*)(.*)$", "g")
                    msg = msg.replace(start_line_regex, img + "$1$2")

                # now replace all images in <a> tags with <img> tags
                pictures = ["png", "jpg", "jpeg", "gif"]
                for pic in pictures
                    # put a br before and after the image
                    pic_regex = new RegExp('<a href="(.*\.' + @escapeForRegex(pic) + ')">(.*)<\/a>', "gim")
                    msg = msg.replace(pic_regex, '<br/><a href="$1"><img alt="$1" src="$1" /></a><br/>')
                
                # replace youtube links with embedded video
                yt_regex = /<a href=".*youtube.com\/watch\?v=([0-9a-zA-Z_-]{11}).*">.*<\/a>/gi
                msg = msg.replace(yt_regex, '<br/><iframe width="560" height="315" src="http://www.youtube.com/embed/$1" frameborder="0" allowfullscreen></iframe><br/>')
                return msg
                

            # replaces links and emails with html links
            createLinks:(msg) ->
                url_regex = /\b((?:https?|ftp):\/\/[a-z0-9+&@#\/%?=~_|!:,.;-]*[a-z0-9+&@#\/%=~_|-])/gi
                pseudo_url_regex = /(^|[^\/])(www\.[\S]+(\b|$))/gi
                email_regex = /\w+@[a-zA-Z_]+?(?:\.[a-zA-Z]{2,6})+/gi
                msg = msg.replace(url_regex, '<a href="$1">$1</a>')
                msg = msg.replace(pseudo_url_regex, '$1<a href="http://$2">$2</a>')
                msg = msg.replace(email_regex, '<a href="mailto:$&">$&</a>')
                return msg


            escapeForRegex: (text) ->
                return (text+'').replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1")


            convertNewLines: (text) ->
                return text.replace('\n', '<br />')



        return MessageModel
    ]
