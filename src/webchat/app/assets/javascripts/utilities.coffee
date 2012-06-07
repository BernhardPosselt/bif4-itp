###
This class binds all click events and gui interactions
###

class Utilities

    # gets the date formatted like year-month-day
    format_timestamp_to_date: (timestamp) ->
        date_string = new Date(timestamp)
        year = date_string.getFullYear()
        month = date_string.getMonth()
        day = date_string.getDay()
        # add preceding zeros when < 10
        if month < 10
            month = "0" + month
        if day < 10
            day = "0" + day
        formatted_date = year + "-" + month + "-" + day
        formatted_date        

    # gets the time formatted like hour:minute
    format_timestamp_to_time: (timestamp) ->
        date_string = new Date(timestamp)
        hours = date_string.getHours()
        minutes = date_string.getMinutes()
        seconds = date_string.getSeconds()
        # add preceding zeros when < 10
        if hours < 10
            hours = "0" + hours
        if minutes < 10
            minutes = "0" + minutes
        if seconds < 10
            seconds = "0" + seconds
        formatted_time = hours + ":" + minutes
        formatted_time
 
    # checks if a word starts with a word
    starts_with: (word, needle) ->
        word = word.toLowerCase()
        needle = needle.toLowerCase()
        return word.indexOf(needle) == 0

    # sorts a hashmap by keys
    sort_by_keys: (dict) ->
        sortedKeys = new Array()
        sortedObj = {}
        for key of dict
            sortedKeys.push(key)
        sortedKeys.sort()
        for key in sortedKeys
            sortedObj[key] = dict[key]
        return sortedObj


    # returns the size of a dictionairy 
    get_dict_size: (dict) ->
        size = 0
        for key of dict
            if dict.hasOwnProperty(key)
                size++
        return size
        
    # escapes chars when put into regex
    regex_esc: (string) ->
        return (string+'').replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1")
        
    # returns kilobyte to a human readable format
    kb_to_human_readable: (bytes, precision) ->
        kilobyte = 1024
        megabyte = kilobyte * 1024;
        gigabyte = megabyte * 1024;
        terabyte = gigabyte * 1024;

        if bytes >= kilobyte and bytes < gigabyte
            return (bytes / kilobyte).toFixed(precision) + ' KB'   
        if bytes >= megabyte and bytes < gigabyte
            return (bytes / megabyte).toFixed(precision) + ' MB'
        else if bytes >= gigabyte and bytes < terabyte
            return (bytes / gigabyte).toFixed(precision) + ' GB'
        else if bytes >= terabyte
            return (bytes / terabyte).toFixed(precision) + ' TB'
        else
            return bytes + ' B'


    # wraps links in <a> tags, pictures in <img> tags 
    # msg: the message we search
    # return: the final message
    sugar_text: (msg) ->
        # first place all urls in <a> tags
        msg = @linkify_text(msg)
        # add smileys
        smileys = new Smileys()
        for key, smile of smileys.get_smileys()
            img = '<img width="50" height="50" alt="' + key + '" src="' + smileys.get_smiley(key) + '" />'
            middle_line_regex = new RegExp(" (" + @regex_esc(key) + ")([\.\?!,;]*) ")
            msg = msg.replace(middle_line_regex, " " + img + "$1$2 ")
            break_line_regex = new RegExp(@regex_esc(key) + "([\.\?!,;]*)<br")
            msg = msg.replace(break_line_regex, img + '$1<br')
            new_line_regex = new RegExp(@regex_esc(key) + "([\.\?!,;]*)\\n")
            msg = msg.replace(new_line_regex, img + '$1\n')
            end_line_regex = new RegExp( "(.*)" + @regex_esc(key) + "([\.\?!,;]*)$", "g")            
            msg = msg.replace(end_line_regex, "$1" + img + "$2")
            start_line_regex = new RegExp( "^" + @regex_esc(key) + "([\.\?!,;]*)(.*)$", "g")
            msg = msg.replace(start_line_regex, img + "$1$2")
        # now replace all images in <a> tags with <img> tags
        pictures = ["png", "jpg", "jpeg", "gif"]
        for pic in pictures
            # put a br before and after the image
            pic_regex = new RegExp('<a href="(.*\.' + @regex_esc(pic) + ')">(.*)<\/a>', "gim")
            msg = msg.replace(pic_regex, '<br/><a href="$1"><img alt="$1" src="$1" /></a><br/>')
        # replace youtube links with embedded video
        yt_regex = /<a href=".*youtube.com\/watch\?v=([0-9a-zA-Z_-]{11}).*">.*<\/a>/gi
        msg = msg.replace(yt_regex, '<br/><iframe width="560" height="315" src="http://www.youtube.com/embed/$1" frameborder="0" allowfullscreen></iframe><br/>')
        return msg
        
    # replaces links and emails with html links
    linkify_text:(msg) ->
        url_regex = /\b((?:https?|ftp):\/\/[a-z0-9+&@#\/%?=~_|!:,.;-]*[a-z0-9+&@#\/%=~_|-])/gi
        pseudo_url_regex = /(^|[^\/])(www\.[\S]+(\b|$))/gi
        email_regex = /\w+@[a-zA-Z_]+?(?:\.[a-zA-Z]{2,6})+/gi
        msg = msg.replace(url_regex, '<a href="$1">$1</a>')
        msg = msg.replace(pseudo_url_regex, '$1<a href="http://$2">$2</a>')
        msg = msg.replace(email_regex, '<a href="mailto:$&">$&</a>')    
        return msg            

