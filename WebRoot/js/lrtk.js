
// JavaScript Document
$(document).ready(function(e) {
	/** *����Ҫ�Զ�������ȥ������** */
	time = window.setInterval(function() {
		$('.og_next').click();
	}, 5000);
	/** *����Ҫ�Զ�������ȥ������** */
	linum = $('.mainlist li').length;// ͼƬ����
	var gundong = 320;// ÿ���ƶ��Ŀ��
	w = linum * 320;// ul���
	$('.piclist').css('width', w + 'px');// ul���
	$('.swaplist').html($('.mainlist').html());// ��������

	$('.og_next').click(function() {

		if ($('.swaplist,.mainlist').is(':animated')) {
			$('.swaplist,.mainlist').stop(true, true);
		}

		if ($('.mainlist li').length > 1) {// ����1��ͼƬ
			ml = parseInt($('.mainlist').css('left'));// Ĭ��ͼƬulλ��
			sl = parseInt($('.swaplist').css('left'));// ����ͼƬulλ��
			if (ml <= 0 && ml > w * -1) {// Ĭ��ͼƬ��ʾʱ
				$('.swaplist').css({
					left : gundong + 'px'
				});// ����ͼƬ������ʾ�����Ҳ�
				$('.mainlist').animate({
					left : ml - gundong + 'px'
				}, 'slow');// Ĭ��ͼƬ����
				if (ml === (w - gundong) * -1) {// Ĭ��ͼƬ���һ��ʱ
					$('.swaplist').animate({
						left : '0px'
					}, 'slow');// ����ͼƬ����
				}
			} else {// ����ͼƬ��ʾʱ
				$('.mainlist').css({
					left : gundong + 'px'
				});// Ĭ��ͼƬ������ʾ������
				$('.swaplist').animate({
					left : sl - gundong + 'px'
				}, 'slow');// ����ͼƬ����
				if (sl === (w - gundong) * -1) {// ����ͼƬ���һ��ʱ
					$('.mainlist').animate({
						left : '0px'
					}, 'slow');// Ĭ��ͼƬ����
				}
			}
		}
	});
	$('.og_prev').click(function() {

		if ($('.swaplist,.mainlist').is(':animated')) {
			$('.swaplist,.mainlist').stop(true, true);
		}

		if ($('.mainlist li').length > 1) {
			ml = parseInt($('.mainlist').css('left'));
			sl = parseInt($('.swaplist').css('left'));
			if (ml <= 0 && ml > w * -1) {
				$('.swaplist').css({
					left : w * -1 + 'px'
				});
				$('.mainlist').animate({
					left : ml + gundong + 'px'
				}, 'slow');
				if (ml === 0) {
					$('.swaplist').animate({
						left : (w - gundong) * -1 + 'px'
					}, 'slow');
				}
			} else {
				$('.mainlist').css({
					left : (w - gundong) * -1 + 'px'
				});
				$('.swaplist').animate({
					left : sl + gundong + 'px'
				}, 'slow');
				if (sl === 0) {
					$('.mainlist').animate({
						left : '0px'
					}, 'slow');
				}
			}
		}
	});
});

$(document).ready(function() {
	$('.og_prev,.og_next').hover(function() {
		$(this).fadeTo('fast', 1);
	}, function() {
		$(this).fadeTo('fast', 0.7);
	});

});
