function sortUnicode(a,b){return a[0].toLowerCase().localeCompare(b[0].toLowerCase());}
function sortIt(o,s,r,n,t,i) {
    o.ord=!o.ord;
    n=o.parentNode.cellIndex;
    r=o.offsetParent.offsetParent.rows;
    var rows=[],cols=[];s=s||1;
    for(i=0;t=r[s+i];i++){
        rows.push(t.cloneNode(true));
        cols.push([t.cells[n].firstChild.nodeValue,i]);
    }
    cols.sort(sortUnicode);
    if(o.ord)cols.reverse()
    for(i=0;t=r[s+i];i++){
        var j = rows[cols[i][1]];
        t.parentNode.replaceChild(j,t);
        j.className=i%2?'odd':'even';
    }
}
function toggleClass(o,s){
    o.oldClassName=o.className
    o.className=s
    o.onmouseout=function(){o.className=o.oldClassName;}
}
