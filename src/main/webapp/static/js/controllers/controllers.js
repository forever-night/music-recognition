navElements = {
    identify : null,
    howitworks : null,
    add : null
};


function loadNavElements() {
    navElements.identify = document.getElementById('navIdentify');
    navElements.howitworks = document.getElementById('navHow');
    navElements.add = document.getElementById('navAdd');
}


function setActive(element) {
    console.log(navElements);
    console.log(element);

    for (var propt in navElements) {
        if (navElements.hasOwnProperty(propt))
            if (element == navElements[propt])
                navElements[propt].setAttribute('class', 'active');
            else
                navElements[propt].removeAttribute('class');
    }
}
