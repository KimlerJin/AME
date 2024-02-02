var addCss = true;
var tour = null;
function tourInit(steps, prevI18N, nextI18N, endI18N) {
    if(addCss){
        initCss();
        addCss = false;
    }
    tour = new Tour({
        backdropContainer: 'body',
        backdropPadding: 0,
        storage: window.localStorage,
        orphan: true,
        steps: steps,
        template: '<div class="popover" role="tooltip"> ' +
            '<div class="arrow"></div> ' +
            '<h3 class="popover-title"></h3> ' +
            '<div class="popover-content"></div> ' +
            '<div class="popover-navigation"> ' +
            '<div class="btn-group"> ' +
            '<button id="btnPrev" class="btn btn-sm btn-default" data-role="prev">&laquo;'+ prevI18N + '</button> ' +
            '<button id="btnNext" class="btn btn-sm btn-primary" data-role="next">' + nextI18N + ' &raquo;</button> ' +
            '<button class="btn btn-sm btn-default" data-role="pause-resume" data-pause-text="Pause" data-resume-text="Resume">Pause</button> ' +
            '</div> ' +
            '<button id="btnEnd" class="btn btn-sm btn-danger" data-role="end">' + endI18N + '</button> ' +
            '</div> ' +
            '</div>',
    });
    tour.init();
    tour.start();
}

/**
 * 跳转到下一步
 */
function nextTourStep(){
    tour.next();
}

function initCss() {
    var cssStyles = '.btn {\n' +
        '  display: inline-block;\n' +
        '  margin-bottom: 0;\n' +
        '  font-weight: normal;\n' +
        '  text-align: center;\n' +
        '  vertical-align: middle;\n' +
        '  touch-action: manipulation;\n' +
        '  cursor: pointer;\n' +
        '  background-image: none;\n' +
        '  border: 1px solid transparent;\n' +
        '  white-space: nowrap;\n' +
        '  padding: 6px 12px;\n' +
        '  font-size: 14px;\n' +
        '  line-height: 1.42857143;\n' +
        '  border-radius: 4px;\n' +
        '  -webkit-user-select: none;\n' +
        '  -moz-user-select: none;\n' +
        '  -ms-user-select: none;\n' +
        '  user-select: none;\n' +
        '}\n' +
        '.btn:focus,\n' +
        '.btn:active:focus,\n' +
        '.btn.active:focus,\n' +
        '.btn.focus,\n' +
        '.btn:active.focus,\n' +
        '.btn.active.focus {\n' +
        '  outline: 5px auto -webkit-focus-ring-color;\n' +
        '  outline-offset: -2px;\n' +
        '}\n' +
        '.btn:hover,\n' +
        '.btn:focus,\n' +
        '.btn.focus {\n' +
        '  color: #333;\n' +
        '  text-decoration: none;\n' +
        '}\n' +
        '.btn:active,\n' +
        '.btn.active {\n' +
        '  outline: 0;\n' +
        '  background-image: none;\n' +
        '  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);\n' +
        '  box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);\n' +
        '}\n' +
        '.btn.disabled,\n' +
        '.btn[disabled],\n' +
        'fieldset[disabled] .btn {\n' +
        '  cursor: not-allowed;\n' +
        '  opacity: 0.65;\n' +
        '  filter: alpha(opacity=65);\n' +
        '  -webkit-box-shadow: none;\n' +
        '  box-shadow: none;\n' +
        '}\n' +
        'a.btn.disabled,\n' +
        'fieldset[disabled] a.btn {\n' +
        '  pointer-events: none;\n' +
        '}\n' +
        '.btn-default {\n' +
        '  color: #333;\n' +
        '  background-color: #fff;\n' +
        '  border-color: #ccc;\n' +
        '}\n' +
        '.btn-default:focus,\n' +
        '.btn-default.focus {\n' +
        '  color: #333;\n' +
        '  background-color: #e6e6e6;\n' +
        '  border-color: #8c8c8c;\n' +
        '}\n' +
        '.btn-default:hover {\n' +
        '  color: #333;\n' +
        '  background-color: #e6e6e6;\n' +
        '  border-color: #adadad;\n' +
        '}\n' +
        '.btn-default:active,\n' +
        '.btn-default.active,\n' +
        '.open > .dropdown-toggle.btn-default {\n' +
        '  color: #333;\n' +
        '  background-color: #e6e6e6;\n' +
        '  border-color: #adadad;\n' +
        '}\n' +
        '.btn-default:active:hover,\n' +
        '.btn-default.active:hover,\n' +
        '.open > .dropdown-toggle.btn-default:hover,\n' +
        '.btn-default:active:focus,\n' +
        '.btn-default.active:focus,\n' +
        '.open > .dropdown-toggle.btn-default:focus,\n' +
        '.btn-default:active.focus,\n' +
        '.btn-default.active.focus,\n' +
        '.open > .dropdown-toggle.btn-default.focus {\n' +
        '  color: #333;\n' +
        '  background-color: #d4d4d4;\n' +
        '  border-color: #8c8c8c;\n' +
        '}\n' +
        '.btn-default:active,\n' +
        '.btn-default.active,\n' +
        '.open > .dropdown-toggle.btn-default {\n' +
        '  background-image: none;\n' +
        '}\n' +
        '.btn-default.disabled:hover,\n' +
        '.btn-default[disabled]:hover,\n' +
        'fieldset[disabled] .btn-default:hover,\n' +
        '.btn-default.disabled:focus,\n' +
        '.btn-default[disabled]:focus,\n' +
        'fieldset[disabled] .btn-default:focus,\n' +
        '.btn-default.disabled.focus,\n' +
        '.btn-default[disabled].focus,\n' +
        'fieldset[disabled] .btn-default.focus {\n' +
        '  background-color: #fff;\n' +
        '  border-color: #ccc;\n' +
        '}\n' +
        '.btn-default .badge {\n' +
        '  color: #fff;\n' +
        '  background-color: #333;\n' +
        '}\n' +
        '.btn-primary {\n' +
        '  color: #fff;\n' +
        '  background-color: #337ab7;\n' +
        '  border-color: #2e6da4;\n' +
        '}\n' +
        '.btn-primary:focus,\n' +
        '.btn-primary.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #286090;\n' +
        '  border-color: #122b40;\n' +
        '}\n' +
        '.btn-primary:hover {\n' +
        '  color: #fff;\n' +
        '  background-color: #286090;\n' +
        '  border-color: #204d74;\n' +
        '}\n' +
        '.btn-primary:active,\n' +
        '.btn-primary.active,\n' +
        '.open > .dropdown-toggle.btn-primary {\n' +
        '  color: #fff;\n' +
        '  background-color: #286090;\n' +
        '  border-color: #204d74;\n' +
        '}\n' +
        '.btn-primary:active:hover,\n' +
        '.btn-primary.active:hover,\n' +
        '.open > .dropdown-toggle.btn-primary:hover,\n' +
        '.btn-primary:active:focus,\n' +
        '.btn-primary.active:focus,\n' +
        '.open > .dropdown-toggle.btn-primary:focus,\n' +
        '.btn-primary:active.focus,\n' +
        '.btn-primary.active.focus,\n' +
        '.open > .dropdown-toggle.btn-primary.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #204d74;\n' +
        '  border-color: #122b40;\n' +
        '}\n' +
        '.btn-primary:active,\n' +
        '.btn-primary.active,\n' +
        '.open > .dropdown-toggle.btn-primary {\n' +
        '  background-image: none;\n' +
        '}\n' +
        '.btn-primary.disabled:hover,\n' +
        '.btn-primary[disabled]:hover,\n' +
        'fieldset[disabled] .btn-primary:hover,\n' +
        '.btn-primary.disabled:focus,\n' +
        '.btn-primary[disabled]:focus,\n' +
        'fieldset[disabled] .btn-primary:focus,\n' +
        '.btn-primary.disabled.focus,\n' +
        '.btn-primary[disabled].focus,\n' +
        'fieldset[disabled] .btn-primary.focus {\n' +
        '  background-color: #337ab7;\n' +
        '  border-color: #2e6da4;\n' +
        '}\n' +
        '.btn-primary .badge {\n' +
        '  color: #337ab7;\n' +
        '  background-color: #fff;\n' +
        '}\n' +
        '.btn-success {\n' +
        '  color: #fff;\n' +
        '  background-color: #5cb85c;\n' +
        '  border-color: #4cae4c;\n' +
        '}\n' +
        '.btn-success:focus,\n' +
        '.btn-success.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #449d44;\n' +
        '  border-color: #255625;\n' +
        '}\n' +
        '.btn-success:hover {\n' +
        '  color: #fff;\n' +
        '  background-color: #449d44;\n' +
        '  border-color: #398439;\n' +
        '}\n' +
        '.btn-success:active,\n' +
        '.btn-success.active,\n' +
        '.open > .dropdown-toggle.btn-success {\n' +
        '  color: #fff;\n' +
        '  background-color: #449d44;\n' +
        '  border-color: #398439;\n' +
        '}\n' +
        '.btn-success:active:hover,\n' +
        '.btn-success.active:hover,\n' +
        '.open > .dropdown-toggle.btn-success:hover,\n' +
        '.btn-success:active:focus,\n' +
        '.btn-success.active:focus,\n' +
        '.open > .dropdown-toggle.btn-success:focus,\n' +
        '.btn-success:active.focus,\n' +
        '.btn-success.active.focus,\n' +
        '.open > .dropdown-toggle.btn-success.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #398439;\n' +
        '  border-color: #255625;\n' +
        '}\n' +
        '.btn-success:active,\n' +
        '.btn-success.active,\n' +
        '.open > .dropdown-toggle.btn-success {\n' +
        '  background-image: none;\n' +
        '}\n' +
        '.btn-success.disabled:hover,\n' +
        '.btn-success[disabled]:hover,\n' +
        'fieldset[disabled] .btn-success:hover,\n' +
        '.btn-success.disabled:focus,\n' +
        '.btn-success[disabled]:focus,\n' +
        'fieldset[disabled] .btn-success:focus,\n' +
        '.btn-success.disabled.focus,\n' +
        '.btn-success[disabled].focus,\n' +
        'fieldset[disabled] .btn-success.focus {\n' +
        '  background-color: #5cb85c;\n' +
        '  border-color: #4cae4c;\n' +
        '}\n' +
        '.btn-success .badge {\n' +
        '  color: #5cb85c;\n' +
        '  background-color: #fff;\n' +
        '}\n' +
        '.btn-info {\n' +
        '  color: #fff;\n' +
        '  background-color: #5bc0de;\n' +
        '  border-color: #46b8da;\n' +
        '}\n' +
        '.btn-info:focus,\n' +
        '.btn-info.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #31b0d5;\n' +
        '  border-color: #1b6d85;\n' +
        '}\n' +
        '.btn-info:hover {\n' +
        '  color: #fff;\n' +
        '  background-color: #31b0d5;\n' +
        '  border-color: #269abc;\n' +
        '}\n' +
        '.btn-info:active,\n' +
        '.btn-info.active,\n' +
        '.open > .dropdown-toggle.btn-info {\n' +
        '  color: #fff;\n' +
        '  background-color: #31b0d5;\n' +
        '  border-color: #269abc;\n' +
        '}\n' +
        '.btn-info:active:hover,\n' +
        '.btn-info.active:hover,\n' +
        '.open > .dropdown-toggle.btn-info:hover,\n' +
        '.btn-info:active:focus,\n' +
        '.btn-info.active:focus,\n' +
        '.open > .dropdown-toggle.btn-info:focus,\n' +
        '.btn-info:active.focus,\n' +
        '.btn-info.active.focus,\n' +
        '.open > .dropdown-toggle.btn-info.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #269abc;\n' +
        '  border-color: #1b6d85;\n' +
        '}\n' +
        '.btn-info:active,\n' +
        '.btn-info.active,\n' +
        '.open > .dropdown-toggle.btn-info {\n' +
        '  background-image: none;\n' +
        '}\n' +
        '.btn-info.disabled:hover,\n' +
        '.btn-info[disabled]:hover,\n' +
        'fieldset[disabled] .btn-info:hover,\n' +
        '.btn-info.disabled:focus,\n' +
        '.btn-info[disabled]:focus,\n' +
        'fieldset[disabled] .btn-info:focus,\n' +
        '.btn-info.disabled.focus,\n' +
        '.btn-info[disabled].focus,\n' +
        'fieldset[disabled] .btn-info.focus {\n' +
        '  background-color: #5bc0de;\n' +
        '  border-color: #46b8da;\n' +
        '}\n' +
        '.btn-info .badge {\n' +
        '  color: #5bc0de;\n' +
        '  background-color: #fff;\n' +
        '}\n' +
        '.btn-warning {\n' +
        '  color: #fff;\n' +
        '  background-color: #f0ad4e;\n' +
        '  border-color: #eea236;\n' +
        '}\n' +
        '.btn-warning:focus,\n' +
        '.btn-warning.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #ec971f;\n' +
        '  border-color: #985f0d;\n' +
        '}\n' +
        '.btn-warning:hover {\n' +
        '  color: #fff;\n' +
        '  background-color: #ec971f;\n' +
        '  border-color: #d58512;\n' +
        '}\n' +
        '.btn-warning:active,\n' +
        '.btn-warning.active,\n' +
        '.open > .dropdown-toggle.btn-warning {\n' +
        '  color: #fff;\n' +
        '  background-color: #ec971f;\n' +
        '  border-color: #d58512;\n' +
        '}\n' +
        '.btn-warning:active:hover,\n' +
        '.btn-warning.active:hover,\n' +
        '.open > .dropdown-toggle.btn-warning:hover,\n' +
        '.btn-warning:active:focus,\n' +
        '.btn-warning.active:focus,\n' +
        '.open > .dropdown-toggle.btn-warning:focus,\n' +
        '.btn-warning:active.focus,\n' +
        '.btn-warning.active.focus,\n' +
        '.open > .dropdown-toggle.btn-warning.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #d58512;\n' +
        '  border-color: #985f0d;\n' +
        '}\n' +
        '.btn-warning:active,\n' +
        '.btn-warning.active,\n' +
        '.open > .dropdown-toggle.btn-warning {\n' +
        '  background-image: none;\n' +
        '}\n' +
        '.btn-warning.disabled:hover,\n' +
        '.btn-warning[disabled]:hover,\n' +
        'fieldset[disabled] .btn-warning:hover,\n' +
        '.btn-warning.disabled:focus,\n' +
        '.btn-warning[disabled]:focus,\n' +
        'fieldset[disabled] .btn-warning:focus,\n' +
        '.btn-warning.disabled.focus,\n' +
        '.btn-warning[disabled].focus,\n' +
        'fieldset[disabled] .btn-warning.focus {\n' +
        '  background-color: #f0ad4e;\n' +
        '  border-color: #eea236;\n' +
        '}\n' +
        '.btn-warning .badge {\n' +
        '  color: #f0ad4e;\n' +
        '  background-color: #fff;\n' +
        '}\n' +
        '.btn-danger {\n' +
        '  color: #fff;\n' +
        '  background-color: #d9534f;\n' +
        '  border-color: #d43f3a;\n' +
        '}\n' +
        '.btn-danger:focus,\n' +
        '.btn-danger.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #c9302c;\n' +
        '  border-color: #761c19;\n' +
        '}\n' +
        '.btn-danger:hover {\n' +
        '  color: #fff;\n' +
        '  background-color: #c9302c;\n' +
        '  border-color: #ac2925;\n' +
        '}\n' +
        '.btn-danger:active,\n' +
        '.btn-danger.active,\n' +
        '.open > .dropdown-toggle.btn-danger {\n' +
        '  color: #fff;\n' +
        '  background-color: #c9302c;\n' +
        '  border-color: #ac2925;\n' +
        '}\n' +
        '.btn-danger:active:hover,\n' +
        '.btn-danger.active:hover,\n' +
        '.open > .dropdown-toggle.btn-danger:hover,\n' +
        '.btn-danger:active:focus,\n' +
        '.btn-danger.active:focus,\n' +
        '.open > .dropdown-toggle.btn-danger:focus,\n' +
        '.btn-danger:active.focus,\n' +
        '.btn-danger.active.focus,\n' +
        '.open > .dropdown-toggle.btn-danger.focus {\n' +
        '  color: #fff;\n' +
        '  background-color: #ac2925;\n' +
        '  border-color: #761c19;\n' +
        '}\n' +
        '.btn-danger:active,\n' +
        '.btn-danger.active,\n' +
        '.open > .dropdown-toggle.btn-danger {\n' +
        '  background-image: none;\n' +
        '}\n' +
        '.btn-danger.disabled:hover,\n' +
        '.btn-danger[disabled]:hover,\n' +
        'fieldset[disabled] .btn-danger:hover,\n' +
        '.btn-danger.disabled:focus,\n' +
        '.btn-danger[disabled]:focus,\n' +
        'fieldset[disabled] .btn-danger:focus,\n' +
        '.btn-danger.disabled.focus,\n' +
        '.btn-danger[disabled].focus,\n' +
        'fieldset[disabled] .btn-danger.focus {\n' +
        '  background-color: #d9534f;\n' +
        '  border-color: #d43f3a;\n' +
        '}\n' +
        '.btn-danger .badge {\n' +
        '  color: #d9534f;\n' +
        '  background-color: #fff;\n' +
        '}\n' +
        '.btn-link {\n' +
        '  color: #337ab7;\n' +
        '  font-weight: normal;\n' +
        '  border-radius: 0;\n' +
        '}\n' +
        '.btn-link,\n' +
        '.btn-link:active,\n' +
        '.btn-link.active,\n' +
        '.btn-link[disabled],\n' +
        'fieldset[disabled] .btn-link {\n' +
        '  background-color: transparent;\n' +
        '  -webkit-box-shadow: none;\n' +
        '  box-shadow: none;\n' +
        '}\n' +
        '.btn-link,\n' +
        '.btn-link:hover,\n' +
        '.btn-link:focus,\n' +
        '.btn-link:active {\n' +
        '  border-color: transparent;\n' +
        '}\n' +
        '.btn-link:hover,\n' +
        '.btn-link:focus {\n' +
        '  color: #23527c;\n' +
        '  text-decoration: underline;\n' +
        '  background-color: transparent;\n' +
        '}\n' +
        '.btn-link[disabled]:hover,\n' +
        'fieldset[disabled] .btn-link:hover,\n' +
        '.btn-link[disabled]:focus,\n' +
        'fieldset[disabled] .btn-link:focus {\n' +
        '  color: #777777;\n' +
        '  text-decoration: none;\n' +
        '}\n' +
        '.btn-lg,\n' +
        '.btn-group-lg > .btn {\n' +
        '  padding: 10px 16px;\n' +
        '  font-size: 18px;\n' +
        '  line-height: 1.3333333;\n' +
        '  border-radius: 6px;\n' +
        '}\n' +
        '.btn-sm,\n' +
        '.btn-group-sm > .btn {\n' +
        '  padding: 5px 10px;\n' +
        '  font-size: 12px;\n' +
        '  line-height: 1.5;\n' +
        '  border-radius: 3px;\n' +
        '}\n' +
        '.btn-xs,\n' +
        '.btn-group-xs > .btn {\n' +
        '  padding: 1px 5px;\n' +
        '  font-size: 12px;\n' +
        '  line-height: 1.5;\n' +
        '  border-radius: 3px;\n' +
        '}\n' +
        '.btn-block {\n' +
        '  display: block;\n' +
        '  width: 100%;\n' +
        '}\n' +
        '.btn-block + .btn-block {\n' +
        '  margin-top: 5px;\n' +
        '}\n' +
        'input[type="submit"].btn-block,\n' +
        'input[type="reset"].btn-block,\n' +
        'input[type="button"].btn-block {\n' +
        '  width: 100%;\n' +
        '}\n' +
        '.btn-group,\n' +
        '.btn-group-vertical {\n' +
        '  position: relative;\n' +
        '  display: inline-block;\n' +
        '  vertical-align: middle;\n' +
        '}\n' +
        '.btn-group > .btn,\n' +
        '.btn-group-vertical > .btn {\n' +
        '  position: relative;\n' +
        '  float: left;\n' +
        '}\n' +
        '.btn-group > .btn:hover,\n' +
        '.btn-group-vertical > .btn:hover,\n' +
        '.btn-group > .btn:focus,\n' +
        '.btn-group-vertical > .btn:focus,\n' +
        '.btn-group > .btn:active,\n' +
        '.btn-group-vertical > .btn:active,\n' +
        '.btn-group > .btn.active,\n' +
        '.btn-group-vertical > .btn.active {\n' +
        '  z-index: 2;\n' +
        '}\n' +
        '.btn-group .btn + .btn,\n' +
        '.btn-group .btn + .btn-group,\n' +
        '.btn-group .btn-group + .btn,\n' +
        '.btn-group .btn-group + .btn-group {\n' +
        '  margin-left: -1px;\n' +
        '}\n' +
        '.btn-toolbar {\n' +
        '  margin-left: -5px;\n' +
        '}\n' +
        '.btn-toolbar .btn,\n' +
        '.btn-toolbar .btn-group,\n' +
        '.btn-toolbar .input-group {\n' +
        '  float: left;\n' +
        '}\n' +
        '.btn-toolbar > .btn,\n' +
        '.btn-toolbar > .btn-group,\n' +
        '.btn-toolbar > .input-group {\n' +
        '  margin-left: 5px;\n' +
        '}\n' +
        '.btn-group > .btn:not(:first-child):not(:last-child):not(.dropdown-toggle) {\n' +
        '  border-radius: 0;\n' +
        '}\n' +
        '.btn-group > .btn:first-child {\n' +
        '  margin-left: 0;\n' +
        '}\n' +
        '.btn-group > .btn:first-child:not(:last-child):not(.dropdown-toggle) {\n' +
        '  border-bottom-right-radius: 0;\n' +
        '  border-top-right-radius: 0;\n' +
        '}\n' +
        '.btn-group > .btn:last-child:not(:first-child),\n' +
        '.btn-group > .dropdown-toggle:not(:first-child) {\n' +
        '  border-bottom-left-radius: 0;\n' +
        '  border-top-left-radius: 0;\n' +
        '}\n' +
        '.btn-group > .btn-group {\n' +
        '  float: left;\n' +
        '}\n' +
        '.btn-group > .btn-group:not(:first-child):not(:last-child) > .btn {\n' +
        '  border-radius: 0;\n' +
        '}\n' +
        '.btn-group > .btn-group:first-child:not(:last-child) > .btn:last-child,\n' +
        '.btn-group > .btn-group:first-child:not(:last-child) > .dropdown-toggle {\n' +
        '  border-bottom-right-radius: 0;\n' +
        '  border-top-right-radius: 0;\n' +
        '}\n' +
        '.btn-group > .btn-group:last-child:not(:first-child) > .btn:first-child {\n' +
        '  border-bottom-left-radius: 0;\n' +
        '  border-top-left-radius: 0;\n' +
        '}\n' +
        '.btn-group .dropdown-toggle:active,\n' +
        '.btn-group.open .dropdown-toggle {\n' +
        '  outline: 0;\n' +
        '}\n' +
        '.btn-group > .btn + .dropdown-toggle {\n' +
        '  padding-left: 8px;\n' +
        '  padding-right: 8px;\n' +
        '}\n' +
        '.btn-group > .btn-lg + .dropdown-toggle {\n' +
        '  padding-left: 12px;\n' +
        '  padding-right: 12px;\n' +
        '}\n' +
        '.btn-group.open .dropdown-toggle {\n' +
        '  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);\n' +
        '  box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);\n' +
        '}\n' +
        '.btn-group.open .dropdown-toggle.btn-link {\n' +
        '  -webkit-box-shadow: none;\n' +
        '  box-shadow: none;\n' +
        '}\n' +
        '.btn .caret {\n' +
        '  margin-left: 0;\n' +
        '}\n' +
        '.btn-lg .caret {\n' +
        '  border-width: 5px 5px 0;\n' +
        '  border-bottom-width: 0;\n' +
        '}\n' +
        '.dropup .btn-lg .caret {\n' +
        '  border-width: 0 5px 5px;\n' +
        '}\n' +
        '.btn-group-vertical > .btn,\n' +
        '.btn-group-vertical > .btn-group,\n' +
        '.btn-group-vertical > .btn-group > .btn {\n' +
        '  display: block;\n' +
        '  float: none;\n' +
        '  width: 100%;\n' +
        '  max-width: 100%;\n' +
        '}\n' +
        '.btn-group-vertical > .btn-group > .btn {\n' +
        '  float: none;\n' +
        '}\n' +
        '.btn-group-vertical > .btn + .btn,\n' +
        '.btn-group-vertical > .btn + .btn-group,\n' +
        '.btn-group-vertical > .btn-group + .btn,\n' +
        '.btn-group-vertical > .btn-group + .btn-group {\n' +
        '  margin-top: -1px;\n' +
        '  margin-left: 0;\n' +
        '}\n' +
        '.btn-group-vertical > .btn:not(:first-child):not(:last-child) {\n' +
        '  border-radius: 0;\n' +
        '}\n' +
        '.btn-group-vertical > .btn:first-child:not(:last-child) {\n' +
        '  border-top-right-radius: 4px;\n' +
        '  border-top-left-radius: 4px;\n' +
        '  border-bottom-right-radius: 0;\n' +
        '  border-bottom-left-radius: 0;\n' +
        '}\n' +
        '.btn-group-vertical > .btn:last-child:not(:first-child) {\n' +
        '  border-top-right-radius: 0;\n' +
        '  border-top-left-radius: 0;\n' +
        '  border-bottom-right-radius: 4px;\n' +
        '  border-bottom-left-radius: 4px;\n' +
        '}\n' +
        '.btn-group-vertical > .btn-group:not(:first-child):not(:last-child) > .btn {\n' +
        '  border-radius: 0;\n' +
        '}\n' +
        '.btn-group-vertical > .btn-group:first-child:not(:last-child) > .btn:last-child,\n' +
        '.btn-group-vertical > .btn-group:first-child:not(:last-child) > .dropdown-toggle {\n' +
        '  border-bottom-right-radius: 0;\n' +
        '  border-bottom-left-radius: 0;\n' +
        '}\n' +
        '.btn-group-vertical > .btn-group:last-child:not(:first-child) > .btn:first-child {\n' +
        '  border-top-right-radius: 0;\n' +
        '  border-top-left-radius: 0;\n' +
        '}\n' +
        '.btn-group-justified {\n' +
        '  display: table;\n' +
        '  width: 100%;\n' +
        '  table-layout: fixed;\n' +
        '  border-collapse: separate;\n' +
        '}\n' +
        '.btn-group-justified > .btn,\n' +
        '.btn-group-justified > .btn-group {\n' +
        '  float: none;\n' +
        '  display: table-cell;\n' +
        '  width: 1%;\n' +
        '}\n' +
        '.btn-group-justified > .btn-group .btn {\n' +
        '  width: 100%;\n' +
        '}\n' +
        '.btn-group-justified > .btn-group .dropdown-menu {\n' +
        '  left: auto;\n' +
        '}\n' +
        '[data-toggle="buttons"] > .btn input[type="radio"],\n' +
        '[data-toggle="buttons"] > .btn-group > .btn input[type="radio"],\n' +
        '[data-toggle="buttons"] > .btn input[type="checkbox"],\n' +
        '[data-toggle="buttons"] > .btn-group > .btn input[type="checkbox"] {\n' +
        '  position: absolute;\n' +
        '  clip: rect(0, 0, 0, 0);\n' +
        '  pointer-events: none;\n' +
        '}\n' +
        '.popover {\n' +
        '  position: absolute;\n' +
        '  top: 0;\n' +
        '  left: 0;\n' +
        '  z-index: 1060;\n' +
        '  display: none;\n' +
        '  width: 300px;\n' +
        '  padding: 1px;\n' +
        '  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;\n' +
        '  font-style: normal;\n' +
        '  font-weight: normal;\n' +
        '  letter-spacing: normal;\n' +
        '  line-break: auto;\n' +
        '  line-height: 1.42857143;\n' +
        '  text-align: left;\n' +
        '  text-align: start;\n' +
        '  text-decoration: none;\n' +
        '  text-shadow: none;\n' +
        '  text-transform: none;\n' +
        '  white-space: normal;\n' +
        '  word-break: normal;\n' +
        '  word-spacing: normal;\n' +
        '  word-wrap: normal;\n' +
        '  font-size: 14px;\n' +
        '  background-color: #fff;\n' +
        '  background-clip: padding-box;\n' +
        '  border: 1px solid #ccc;\n' +
        '  border: 1px solid rgba(0, 0, 0, 0.2);\n' +
        '  border-radius: 6px;\n' +
        '  -webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);\n' +
        '  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);\n' +
        '}\n' +
        '.popover.top {\n' +
        '  margin-top: -10px;\n' +
        '}\n' +
        '.popover.right {\n' +
        '  margin-left: 10px;\n' +
        '}\n' +
        '.popover.bottom {\n' +
        '  margin-top: 10px;\n' +
        '}\n' +
        '.popover.left {\n' +
        '  margin-left: -10px;\n' +
        '}\n' +
        '.popover-title {\n' +
        '  margin: 0;\n' +
        '  padding: 8px 14px;\n' +
        '  font-size: 14px;\n' +
        '  background-color: #f7f7f7;\n' +
        '  border-bottom: 1px solid #ebebeb;\n' +
        '  border-radius: 5px 5px 0 0;\n' +
        '}\n' +
        '.popover-content {\n' +
        '  padding: 9px 14px;\n' +
        '  height: 80px;\n' +
        '}\n' +
        '.popover > .arrow,\n' +
        '.popover > .arrow:after {\n' +
        '  position: absolute;\n' +
        '  display: block;\n' +
        '  width: 0;\n' +
        '  height: 0;\n' +
        '  border-color: transparent;\n' +
        '  border-style: solid;\n' +
        '}\n' +
        '.popover > .arrow {\n' +
        '  border-width: 11px;\n' +
        '}\n' +
        '.popover > .arrow:after {\n' +
        '  border-width: 10px;\n' +
        '  content: "";\n' +
        '}\n' +
        '.popover.top > .arrow {\n' +
        '  left: 50%;\n' +
        '  margin-left: -11px;\n' +
        '  border-bottom-width: 0;\n' +
        '  border-top-color: #999999;\n' +
        '  border-top-color: rgba(0, 0, 0, 0.25);\n' +
        '  bottom: -11px;\n' +
        '}\n' +
        '.popover.top > .arrow:after {\n' +
        '  content: " ";\n' +
        '  bottom: 1px;\n' +
        '  margin-left: -10px;\n' +
        '  border-bottom-width: 0;\n' +
        '  border-top-color: #fff;\n' +
        '}\n' +
        '.popover.right > .arrow {\n' +
        '  top: 50%;\n' +
        '  left: -11px;\n' +
        '  margin-top: -11px;\n' +
        '  border-left-width: 0;\n' +
        '  border-right-color: #999999;\n' +
        '  border-right-color: rgba(0, 0, 0, 0.25);\n' +
        '}\n' +
        '.popover.right > .arrow:after {\n' +
        '  content: " ";\n' +
        '  left: 1px;\n' +
        '  bottom: -10px;\n' +
        '  border-left-width: 0;\n' +
        '  border-right-color: #fff;\n' +
        '}\n' +
        '.popover.bottom > .arrow {\n' +
        '  left: 50%;\n' +
        '  margin-left: -11px;\n' +
        '  border-top-width: 0;\n' +
        '  border-bottom-color: #999999;\n' +
        '  border-bottom-color: rgba(0, 0, 0, 0.25);\n' +
        '  top: -11px;\n' +
        '}\n' +
        '.popover.bottom > .arrow:after {\n' +
        '  content: " ";\n' +
        '  top: 1px;\n' +
        '  margin-left: -10px;\n' +
        '  border-top-width: 0;\n' +
        '  border-bottom-color: #fff;\n' +
        '}\n' +
        '.popover.left > .arrow {\n' +
        '  top: 50%;\n' +
        '  right: -11px;\n' +
        '  margin-top: -11px;\n' +
        '  border-right-width: 0;\n' +
        '  border-left-color: #999999;\n' +
        '  border-left-color: rgba(0, 0, 0, 0.25);\n' +
        '}\n' +
        '.popover.left > .arrow:after {\n' +
        '  content: " ";\n' +
        '  right: 1px;\n' +
        '  border-right-width: 0;\n' +
        '  border-left-color: #fff;\n' +
        '  bottom: -10px;\n' +
        '}\n' +
        '.fade {\n' +
        '  opacity: 0;\n' +
        '  -webkit-transition: opacity 0.15s linear;\n' +
        '  -o-transition: opacity 0.15s linear;\n' +
        '  transition: opacity 0.15s linear;\n' +
        '}\n' +
        '.fade.in {\n' +
        '  opacity: 1;\n' +
        '}\n' +
        '.collapse {\n' +
        '  display: none;\n' +
        '}\n' +
        '.collapse.in {\n' +
        '  display: block;\n' +
        '}\n' +
        'tr.collapse.in {\n' +
        '  display: table-row;\n' +
        '}\n' +
        'tbody.collapse.in {\n' +
        '  display: table-row-group;\n' +
        '}\n' +
        '.collapsing {\n' +
        '  position: relative;\n' +
        '  height: 0;\n' +
        '  overflow: hidden;\n' +
        '  -webkit-transition-property: height, visibility;\n' +
        '  transition-property: height, visibility;\n' +
        '  -webkit-transition-duration: 0.35s;\n' +
        '  transition-duration: 0.35s;\n' +
        '  -webkit-transition-timing-function: ease;\n' +
        '  transition-timing-function: ease;\n' +
        '}\n' +
        '.tour-backdrop {\n' +
        '  position: absolute;\n' +
        '  z-index: 1100;\n' +
        '  background-color: #000;\n' +
        '  opacity: 0.8;\n' +
        '  filter: alpha(opacity=80);\n' +
        '}\n' +
        '.popover[class*="tour-"] {\n' +
        '  z-index: 1102;\n' +
        '}\n' +
        '.popover[class*="tour-"] .popover-navigation {\n' +
        '  padding: 9px 14px;\n' +
        '  overflow: hidden;\n' +
        '}\n' +
        '.popover[class*="tour-"] .popover-navigation *[data-role="end"] {\n' +
        '  float: right;\n' +
        '}\n' +
        '.popover[class*="tour-"] .popover-navigation *[data-role="prev"],\n' +
        '.popover[class*="tour-"] .popover-navigation *[data-role="next"],\n' +
        '.popover[class*="tour-"] .popover-navigation *[data-role="end"] {\n' +
        '  cursor: pointer;\n' +
        '}\n' +
        '.popover[class*="tour-"] .popover-navigation *[data-role="prev"].disabled,\n' +
        '.popover[class*="tour-"] .popover-navigation *[data-role="next"].disabled,\n' +
        '.popover[class*="tour-"] .popover-navigation *[data-role="end"].disabled {\n' +
        '  cursor: default;\n' +
        '}\n' +
        '.popover[class*="tour-"].orphan {\n' +
        '  position: fixed;\n' +
        '  margin-top: 0;\n' +
        '}\n' +
        '.popover[class*="tour-"].orphan .arrow {\n' +
        '  display: none;\n' +
        '}\n';

    var styleElement = document.createElement('style');
    styleElement.type = "text/css";
    if (styleElement.styleSheet) {
        styleElement.styleSheet.cssText = cssStyles;
    } else {
        styleElement.appendChild(document.createTextNode(cssStyles));
    }
    document.head.appendChild(styleElement);
}