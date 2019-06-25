layui.define('form', function (exports) {
    "use strict";
  
    let $ = layui.$
        , form = layui.form
        , layarea = {
      _id: 0
      , config: {}
      , set: function (options) {
        let that = this;
        that.config = $.extend({}, that.config, options);
        return that;
      }
      , on: function (events, callback) {
        return layui.onevent.call(this, 'layarea', events, callback);
      }
    }
        , thisArea = function () {
      let that = this;
      return {
        layarea: function (files) {
          that.layarea.call(that, files);
        }
        , config: that.config
      }
    }
        , Class = function (options) {
      let that = this;
      that.config = $.extend({}, that.config, layarea.config, options);
      that.render();
    };
  
    let areaList = {
      province_list: {
        120000: '天津市'
      },
      city_list: {
        120100: '天津市'
      },
      county_list: {
        120101: '和平区',
        120102: '河东区',
        120103: '河西区',
        120104: '南开区',
        120105: '河北区',
        120106: '红桥区',
        120110: '东丽区',
        120111: '西青区',
        120112: '津南区',
        120113: '北辰区',
        120114: '武清区',
        120115: '宝坻区',
        120116: '滨海新区',
        120117: '宁河区',
        120118: '静海区',
        120119: '蓟州区'
      }
    };
  
  
    Class.prototype.config = {
      elem: '',
      parentFilter:'',
      data: {
        province: '--选择省--',
        city: '--选择市--',
        county: '--选择区--',
      },
      change: function(result){}
    };
  
    Class.prototype.index = 0;
  
    Class.prototype.render = function () {
      let that = this, options = that.config;
      options.elem = $(options.elem);
      options.bindAction = $(options.bindAction);
  
      that.events();
    };
  
    Class.prototype.events = function () {
      let that = this, options = that.config, index;
      let provinceFilter = 'province-' + layarea._id;
      let cityFilter = 'city-' + layarea._id;
      let countyFilter = 'county-' + layarea._id;
  
      let provinceEl = options.elem.find('.province-selector');
      let cityEl = options.elem.find('.city-selector');
      let countyEl = options.elem.find('.county-selector');
  
      //filter
      if(provinceEl.attr('lay-filter')){
        provinceFilter = provinceEl.attr('lay-filter');
      }
      if(cityEl.attr('lay-filter')){
        cityFilter = cityEl.attr('lay-filter');
      }
      if(countyEl.attr('lay-filter')){
        countyFilter = countyEl.attr('lay-filter');
      }
      provinceEl.attr('lay-filter', provinceFilter);
      cityEl.attr('lay-filter', cityFilter);
      countyEl.attr('lay-filter', countyFilter);
  
      //获取默认值
      if(provinceEl.data('value')){
        options.data.province = provinceEl.data('value');
      }
      if(cityEl.data('value')){
        options.data.city = cityEl.data('value');
      }
      if(countyEl.data('value')){
        options.data.county = countyEl.data('value');
      }
      provinceEl.attr('lay-filter', provinceFilter);
      cityEl.attr('lay-filter', cityFilter);
      countyEl.attr('lay-filter', countyFilter);
  
      //监听结果
      form.on('select('+provinceFilter+')', function(data){
        options.data.province = data.value;
        let code = getCode('province', data.value);
        renderCity(code);
  
        options.change(options.data);
      });
      form.on('select('+cityFilter+')', function(data){
        options.data.city = data.value;
        let code = getCode('city', data.value);
        renderCounty(code);
  
        options.change(options.data);
      });
      form.on('select('+countyFilter+')', function(data){
        options.data.county = data.value;
  
        options.change(options.data);
      });
  
      renderProvince();
  
      //查找province
      function renderProvince(){
        let tpl = '';
        let provinceList = getList("province");
        let currentCode = '';
        let currentName = '';
        provinceList.forEach(function(_item){
          if (!currentCode){
            currentCode = _item.code;
            currentName = _item.name;
          }
          if(_item.name === options.data.province){
            currentCode = _item.code;
            currentName = _item.name;
          }
          tpl += '<option value="'+_item.name+'">'+_item.name+'</option>';
        });
        options.data.province = currentName;
        provinceEl.html(tpl);
        provinceEl.val(options.data.province);
        form.render('select',options.parentFilter);
        renderCity(currentCode);
      }
  
      function renderCity(provinceCode){
        let tpl = '';
        let cityList = getList('city', provinceCode.slice(0, 2));
        let currentCode = '';
        let currentName = '';
        cityList.forEach(function(_item){
          if (!currentCode){
            currentCode = _item.code;
            currentName = _item.name;
          }
          if(_item.name === options.data.city){
            currentCode = _item.code;
            currentName = _item.name;
          }
          tpl += '<option value="'+_item.name+'">'+_item.name+'</option>';
        });
        options.data.city = currentName;
        cityEl.html(tpl);
        cityEl.val(options.data.city);
        form.render('select',options.parentFilter);
        renderCounty(currentCode);
      }
  
      function renderCounty(cityCode){
        let tpl = '';
        let countyList = getList('county', cityCode.slice(0, 4));
        let currentCode = '';
        let currentName = '';
        countyList.forEach(function(_item){
          if (!currentCode){
            currentCode = _item.code;
            currentName = _item.name;
          }
          if(_item.name === options.data.county){
            currentCode = _item.code;
            currentName = _item.name;
          }
          tpl += '<option value="'+_item.name+'">'+_item.name+'</option>';
        });
        options.data.county = currentName;
        countyEl.html(tpl);
        countyEl.val(options.data.county);
  
        form.render('select',options.parentFilter);
      }
  
      function getList(type, code) {
        let result = [];
  
        if (type !== 'province' && !code) {
          return result;
        }
  
        let list = areaList[type + "_list"] || {};
        result = Object.keys(list).map(function (code) {
          return {
            code: code,
            name: list[code]
          };
        });
  
        if (code) {
          // oversea code
          if (code[0] === '9' && type === 'city') {
            code = '9';
          }
  
          result = result.filter(function (item) {
            return item.code.indexOf(code) === 0;
          });
        }
  
        return result;
      }
  
      function getCode(type, name){
        let code = '';
        let list = areaList[type + "_list"] || {};
        layui.each(list, function(_code, _name){
          if(_name === name){
            code = _code;
          }
        });
  
        return code;
      }
    };
  
    layarea.render = function (options) {
      let inst = new Class(options);
      layarea._id++;
      return thisArea.call(inst);
    };
  
    //暴露接口
    exports('layarea', layarea);
  });